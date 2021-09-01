/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import control.action.Action;
import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuizSection;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DyscalculiaQuizModel;
import model.QuizSectionModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class QuizSectionAction extends Action {

    private QuizSectionModel quizSectionModel;
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    
    public QuizSectionAction() {
    	quizSectionModel = new QuizSectionModel();
    	dyscalculiaQuizModel = new DyscalculiaQuizModel();
    }
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        List<QuizSection> sections = quizSectionModel.doRetrieveAll();
        List<DyscalculiaQuiz> quizzes = dyscalculiaQuizModel.doRetrieveAll();
        
        //Needed because result lists are read-only
        List<DyscalculiaQuiz> listOfQuizzes = new ArrayList<DyscalculiaQuiz>();
        listOfQuizzes.addAll(quizzes);
        
        request.setAttribute("sections", sections);
        request.setAttribute("quizzes", listOfQuizzes);
        
        return "protected/quizSections";
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    protected String getResponseType() {
        return Action.FORWARD_RESPONSE;
    }
    
}
