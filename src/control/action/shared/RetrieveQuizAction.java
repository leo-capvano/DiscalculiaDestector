/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import entity.Institute;
import entity.account.Teacher;
import entity.quiz.DyscalculiaQuiz;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DyscalculiaQuizModel;
import model.InstituteModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class RetrieveQuizAction extends Action {

    private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request";
    
    private Gson gson;
    
    private DyscalculiaQuizModel quizModel;
    private InstituteModel instituteModel;

    public RetrieveQuizAction() {
		gson = GsonProducer.getGson();
		
		quizModel = new DyscalculiaQuizModel();
		instituteModel = new InstituteModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        String askFor = request.getParameter("askFor");
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        
        List<DyscalculiaQuiz> quizzes = null;
        
        switch(askFor) {
            case "trustedQuizzes":
                quizzes = quizModel.doRetrieveAllTrusted();
                break;
            case "instituteQuizzes":
                String instituteIDString = request.getParameter("instituteID");
                
                int instituteID = Integer.parseInt(instituteIDString);
                Institute institute = instituteModel.doRetrieveById(instituteID);
                
                if(institute == null) {
                    catchInvalidRequestError(response);
                    return null;
                }
                
                List<Teacher> teachers = institute.getTeachers();
                
                quizzes = new ArrayList<DyscalculiaQuiz>();
                
                for(Teacher teacher : teachers) {
                    quizzes.addAll(quizModel.doRetrieveByTeacher(teacher.getId()));
                }
                
                break;
            case "allQuizzes":
                quizzes = quizModel.doRetrieveAll();
                break;
        }
        
        jsonResponse.setJsonResponseStatus(true);
        jsonResponse.addContent("quizList", quizzes);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String askFor = request.getParameter("askFor");
        
        if(askFor == null)
            return false;
        
        switch(askFor) {
            case "trustedQuizzes":
            case "allQuizzes":
                return true;
            case "instituteQuizzes":
                String instituteIDString = request.getParameter("instituteID");

                try {
                    Integer.parseInt(instituteIDString);
                    return true;
                } catch(NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        
        jsonResponse.setJsonResponseStatus(false);
        jsonResponse.setJsonResponseMessage(MALFORMED_REQUEST_MESSAGE);
        writer.print(gson.toJson(jsonResponse));
    }


    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
}
