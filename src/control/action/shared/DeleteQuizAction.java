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
public class DeleteQuizAction extends Action {

    private static final String INVALID_REQUEST_MESSAGE = "Malformed request";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    
    private Gson gson;
    
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    
    public DeleteQuizAction() {
		dyscalculiaQuizModel = new DyscalculiaQuizModel();
		gson = GsonProducer.getGson();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        int quizID = Integer.parseInt(request.getParameter("id"));
        
        DyscalculiaQuiz toDelete = dyscalculiaQuizModel.doRetrieveById(quizID);
        if(toDelete == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || loggedAccount instanceof DyscalculiaPatient || (!(loggedAccount instanceof Administrator) && toDelete.isTrusted()) ) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        dyscalculiaQuizModel.doDelete(toDelete);
        
        jsonResponse = new JsonResponse(true);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String quizID = request.getParameter("id");
        
        try {
            Integer.parseInt(quizID);
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
