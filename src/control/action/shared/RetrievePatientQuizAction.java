/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.action.shared;

import control.action.Action;
import entity.Institute;
import entity.QuizStatistics;
import entity.account.Account;
import entity.account.DyscalculiaPatient;
import entity.account.Teacher;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DyscalculiaPatientModel;
import model.QuizStatisticsModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class RetrievePatientQuizAction extends Action {

    private QuizStatisticsModel quizStatisticsModel;
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    
    public RetrievePatientQuizAction() {
		quizStatisticsModel = new QuizStatisticsModel();
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        DyscalculiaPatient patient = dyscalculiaPatientModel.doRetrieveById(id);
        
        if(patient == null) {
            catchInvalidRequestError(response);
            return "protected/quizstatistics";
        }
        
        List<QuizStatistics> quizList = quizStatisticsModel.doRetrieveByDyscalculiaPatient(patient);
        
        request.setAttribute("quizList", quizList);
        return "protected/quizstatistics";
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String idParameter = request.getParameter("id");
        
        int id;
        
        try{
            id = Integer.parseInt(idParameter);
        } catch(NumberFormatException e ) {
            return false;
        }
        
        HttpSession session = request.getSession();
        Account loggedIn = (Account) session.getAttribute("account");
        
        if(loggedIn == null)
            return false;
        
        if(loggedIn instanceof DyscalculiaPatient) {
            //A patient can only see his results
            DyscalculiaPatient patient = (DyscalculiaPatient) loggedIn;
            
            return patient.getId() == id;
        }
        
        if(loggedIn instanceof Teacher) {
            //A teacher can only see the results of his students
            Teacher teacher = (Teacher) loggedIn;
            List<Institute> institutes = teacher.getInstitutes();
            
            for(Institute institute : institutes) {
                List<DyscalculiaPatient> patients = institute.getStudents();
                for(int i = 0; i < patients.size(); i++) {
                    DyscalculiaPatient patient = patients.get(i);
                    if(patient.getId() == id)
                        return true;
                }
            }
            
            return false;
        }
        
        return true;
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        response.setHeader(HEADER_NAME, INVALID_RESPONSE);
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected String getResponseType() {
        return Action.FORWARD_RESPONSE;
    }

}
