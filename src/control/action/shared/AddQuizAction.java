/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import entity.account.Account;
import entity.account.Administrator;
import entity.account.DyscalculiaPatient;
import entity.account.Teacher;
import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuizSection;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DyscalculiaQuizModel;
import model.QuizSectionModel;
import model.TeacherModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class AddQuizAction extends Action {

    private static final String INVALID_REQUEST_MESSAGE = "Malformed request";
    
    private QuizSectionModel quizSectionModel;
    private TeacherModel teacherModel;
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    
    private Gson gson;
    
    public AddQuizAction() {
		quizSectionModel = new QuizSectionModel();
		teacherModel = new TeacherModel();
		dyscalculiaQuizModel = new DyscalculiaQuizModel();
		
		gson = GsonProducer.getGson();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
                
        int sectionID = Integer.parseInt(request.getParameter("sectionID"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        QuizSection quizSection = quizSectionModel.doRetrieveById(sectionID);
        if(quizSection == null) {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(INVALID_REQUEST_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            
            return null;
        }
        
        DyscalculiaQuiz quiz = new DyscalculiaQuiz(name, description, null, null, null, quizSection);
        
        Account account = (Account) request.getSession().getAttribute("account");
        if(account instanceof Administrator)
            quiz.setTrusted(true);
        else {
            System.out.println(account.getId());
            Teacher teacher = teacherModel.doUpdate((Teacher) account);
            quiz.setTeacher(teacher);
            quiz.setTrusted(false);
        }
        
        dyscalculiaQuizModel.doSave(quiz);
        
        jsonResponse.setJsonResponseStatus(true);
        jsonResponse.addContent("quiz", quiz);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        Account account = (Account) request.getSession().getAttribute("account");
        
        if(account == null || (account instanceof DyscalculiaPatient))
            return false;
        
        String sectionID = request.getParameter("sectionID");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        try {
            Integer.parseInt(sectionID);
        } catch(NumberFormatException e) {
            return false;
        }
        
        if(name == null || name.equals("") || description == null || description.equals(""))
            return false;
        else
            return true;
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        
        jsonResponse.setJsonResponseStatus(false);
        jsonResponse.setJsonResponseMessage(INVALID_REQUEST_MESSAGE);
        writer.println(gson.toJson(jsonResponse));
    }
    
    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
}
