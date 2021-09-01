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
import entity.quiz.DyscalculiaQuiz;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DyscalculiaQuizModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class EditQuizAction extends Action {

    private static final String INVALID_REQUEST_MESSAGE = "Malformed request";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";

    private Gson gson;
    
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    
    public EditQuizAction() {
    	gson = GsonProducer.getGson();
    	dyscalculiaQuizModel = new DyscalculiaQuizModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        
        int quizID = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String approprieteThreshold = request.getParameter("appropriete");
        String lowRiskThreshold = request.getParameter("lowRisk");
        String highRiskThreshold = request.getParameter("highRisk");
        
        DyscalculiaQuiz quiz = dyscalculiaQuizModel.doRetrieveById(quizID);
        if(quiz == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || loggedAccount instanceof DyscalculiaPatient || (!(loggedAccount instanceof Administrator) && quiz.isTrusted()) ) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        if(name != null)
            quiz.setName(name);
        
        if(approprieteThreshold != null) {
            double appropriete = Double.parseDouble(approprieteThreshold);
            quiz.setApproprieteThreshold(appropriete);
        }
        
        if(lowRiskThreshold != null) {
            double lowRisk = Double.parseDouble(lowRiskThreshold);
            quiz.setLowRiskThreshold(lowRisk);
        }
        
        if(highRiskThreshold != null) {
            double highRisk = Double.parseDouble(highRiskThreshold);
            quiz.setHighRiskThreshold(highRisk);
        }
        
        dyscalculiaQuizModel.doUpdate(quiz);
        
        jsonResponse = new JsonResponse(true);
        jsonResponse.addContent("trusted", quiz.isTrusted());
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String quizID = request.getParameter("id");
        String approprieteThreshold = request.getParameter("appropriete");
        String lowRiskThreshold = request.getParameter("lowRisk");
        String highRiskThreshold = request.getParameter("highRisk");
        
        try {
            Integer.parseInt(quizID);
            
            if(approprieteThreshold != null)
                Double.parseDouble(approprieteThreshold);
            
            if(lowRiskThreshold != null)
                Double.parseDouble(lowRiskThreshold);
            
            if(highRiskThreshold != null)
                Double.parseDouble(highRiskThreshold);
            
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
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
