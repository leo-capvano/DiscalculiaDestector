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
import entity.quiz.QuestionSection;
import entity.quiz.QuestionType;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DyscalculiaQuizModel;
import model.QuestionSectionModel;
import model.QuestionTypeModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class AddQuestionSectionAction extends Action {

    private static final String INVALID_NUM_OF_CHOICES = "Il numero delle scelte deve essere al massimo ";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    private static final String DUPLICATE_ENTRY_MESSAGE = "Hai gia' creato una sezione con questo nome";
    
    private Gson gson;

    private DyscalculiaQuizModel dyscalculiaQuizModel;
    private QuestionTypeModel questionTypeModel;
    private QuestionSectionModel questionSectionModel;
    
    public AddQuestionSectionAction() {
		gson = GsonProducer.getGson();
		dyscalculiaQuizModel = new DyscalculiaQuizModel();
		questionTypeModel = new QuestionTypeModel();
		questionSectionModel = new QuestionSectionModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        
        int quizID = Integer.parseInt(request.getParameter("quizID"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int questionTypeID = Integer.parseInt(request.getParameter("questionTypeID"));
        boolean isMultipleChoice = Boolean.valueOf(request.getParameter("isMultipleChoice"));
        Integer numOfChoices = null;
        
        if(isMultipleChoice)
            numOfChoices = Integer.parseInt(request.getParameter("numOfChoices"));
        
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
        
        QuestionType questionType = questionTypeModel.doRetrieveById(questionTypeID);
        if(questionType == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        if(numOfChoices != null) {
            int maxChoices = questionType.getMaxMultipleChoices();
            if(numOfChoices > maxChoices) {
                jsonResponse = new JsonResponse(false, INVALID_NUM_OF_CHOICES + maxChoices);
                writer.println(gson.toJson(jsonResponse));
                return null;
            }
        }
        
        QuestionSection duplicate = questionSectionModel.doRetrieveByNameAndQuiz(name, quiz);
        if(duplicate != null) {
            jsonResponse = new JsonResponse(false, DUPLICATE_ENTRY_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        QuestionSection questionSection = new QuestionSection(name, description, numOfChoices, questionType, quiz);
        questionSectionModel.doSave(questionSection);
        
        jsonResponse = new JsonResponse(true);
        jsonResponse.addContent("questionSection", questionSection);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String quizID = request.getParameter("quizID");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String questionTypeID = request.getParameter("questionTypeID");
        String numOfChoices = request.getParameter("numOfChoices");
        String isMultipleChoice = request.getParameter("isMultipleChoice");
        
        if(name == null || name.equals("") || description == null || description.equals(""))
            return false;
        
        try {
            Integer.parseInt(quizID);
            Integer.parseInt(questionTypeID);
            boolean isMulti = Boolean.valueOf(isMultipleChoice);
            if(isMulti)
                Integer.parseInt(numOfChoices);
            
            return true;
        } catch(NumberFormatException | NullPointerException e) {
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
