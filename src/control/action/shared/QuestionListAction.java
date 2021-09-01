/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import control.action.Action;
import entity.quiz.QuestionSection;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.QuestionSectionModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class QuestionListAction extends Action {

    private QuestionSectionModel questionSectionModel;
    
    public QuestionListAction() {
		questionSectionModel = new QuestionSectionModel();
	}
    
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        int questionSectionID = Integer.parseInt(request.getParameter("questionSection"));
        
        QuestionSection questionSection = questionSectionModel.doRetrieveById(questionSectionID);
        if(questionSection == null) {
            catchInvalidRequestError(response);
            return "protected/questionsList";
        }
        
        request.setAttribute("questionSection", questionSection);
        
        return "protected/questionsList";
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String questionSectionID = request.getParameter("questionSection");
        
        try {
            Integer.parseInt(questionSectionID);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected String getResponseType() {
        return Action.FORWARD_RESPONSE;
    }
    
}
