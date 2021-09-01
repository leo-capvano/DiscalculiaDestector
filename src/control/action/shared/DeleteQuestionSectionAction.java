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
import entity.quiz.QuestionSection;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.QuestionSectionModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class DeleteQuestionSectionAction extends Action {

    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    
    private Gson gson;
    
    private QuestionSectionModel questionSectionModel;
    
    public DeleteQuestionSectionAction() {
		gson = GsonProducer.getGson();
		questionSectionModel = new QuestionSectionModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        
        int questionSectionID = Integer.parseInt(request.getParameter("id"));
        
        QuestionSection toDelete = questionSectionModel.doRetrieveById(questionSectionID);
        
        if(toDelete == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || loggedAccount instanceof DyscalculiaPatient || (!(loggedAccount instanceof Administrator) && toDelete.getQuiz().isTrusted()) ) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        questionSectionModel.doDelete(toDelete);
        
        jsonResponse = new JsonResponse(true);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String questionSectionID = request.getParameter("id");
        
        try {
            Integer.parseInt(questionSectionID);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse(false, "Malformed Request");
        PrintWriter writer = response.getWriter();
        
        writer.println(gson.toJson(jsonResponse));
    }
    
    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
}
