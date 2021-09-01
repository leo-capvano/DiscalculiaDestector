/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.admin;

import com.google.gson.Gson;
import control.action.Action;
import entity.account.Account;
import entity.account.Administrator;
import entity.quiz.QuizSection;
import model.QuizSectionModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class AddQuizSectionAction extends Action {
    
    private static final String INVALID_REQUEST_MESSAGE = "Malformed request";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    
    private Gson gson;
    
    private QuizSectionModel quizSectionModel;
    
    public AddQuizSectionAction() {
		gson = GsonProducer.getGson();
		quizSectionModel = new QuizSectionModel();
	}
    
    @Override
    public String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || !(loggedAccount instanceof Administrator)) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        QuizSection quizSection = new QuizSection(name, description);
        quizSectionModel.doSave(quizSection);
        quizSection = quizSectionModel.doUpdate(quizSection);
        
        jsonResponse = new JsonResponse(true);
        jsonResponse.addContent("id", quizSection.getId());
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        return !(name == null || description == null);
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
