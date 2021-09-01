/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuestionType;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DyscalculiaQuizModel;
import model.QuestionTypeModel;
import utility.GsonProducer;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class QuizDetailAction extends Action {

    private DyscalculiaQuizModel dyscalculiaQuizModel;
    private QuestionTypeModel questionTypeModel;
    
    private Gson gson;
    
    public QuizDetailAction() {
		gson = GsonProducer.getGson();
		
		questionTypeModel = new QuestionTypeModel();
		dyscalculiaQuizModel = new DyscalculiaQuizModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        int quizID = Integer.parseInt(request.getParameter("id"));
        
        DyscalculiaQuiz quiz = dyscalculiaQuizModel.doRetrieveById(quizID);
        if(quiz == null) {
            catchInvalidRequestError(response);
            return "protected/quizDetails";
        }
        
        List<QuestionType> questionTypes = questionTypeModel.doRetrieveAll();
        
        request.setAttribute("quiz", quiz);
        request.setAttribute("quizSection", quiz.getQuizSection());
        request.setAttribute("questionSectionList", quiz.getSections());
        request.setAttribute("questionTypes", questionTypes);
        request.setAttribute("questionTypesJSON", gson.toJson(questionTypes));
        
        return "protected/quizDetails";
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
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    
    @Override
    protected String getResponseType() {
        return Action.FORWARD_RESPONSE;
    }
    
}
