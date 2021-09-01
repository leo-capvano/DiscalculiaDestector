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
import model.QuizStatisticsModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class StatisticDetailsAction extends Action {

    private QuizStatisticsModel quizStatisticsModel;
    
    public StatisticDetailsAction() {
		quizStatisticsModel = new QuizStatisticsModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        int id = Integer.parseInt(request.getParameter("detailID"));
        
        QuizStatistics quizStatistics = quizStatisticsModel.doRetrieveById(id);
        
        request.setAttribute("quizStatistics", quizStatistics);
        
        return "protected/detailed-statistic";
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String idParameter = request.getParameter("detailID");
        
        int id;
        
        try{
            id = Integer.parseInt(idParameter);
        } catch(NumberFormatException e ) {
            return false;
        }
        
        QuizStatistics quizStatistics = quizStatisticsModel.doRetrieveById(id);
        
        if(quizStatistics == null) {
            return false;
        }
        
        HttpSession session = request.getSession();
        Account loggedIn = (Account) session.getAttribute("account");
        
        if(loggedIn == null)
            return false;
        
        if(loggedIn instanceof DyscalculiaPatient) {
            //A patient can only see his results
            DyscalculiaPatient patient = (DyscalculiaPatient) loggedIn;
            
            return patient.getId() == quizStatistics.getPatient().getId();
        }
        
        if(loggedIn instanceof Teacher) {
            //A teacher can only see the results of his students
            Teacher teacher = (Teacher) loggedIn;
            List<Institute> institutes = teacher.getInstitutes();
            
            for(Institute institute : institutes) {
                List<DyscalculiaPatient> patients = institute.getStudents();
                for(int i = 0; i < patients.size(); i++) {
                    DyscalculiaPatient patient = patients.get(i);
                    if(patient.getId() == quizStatistics.getPatient().getId())
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
