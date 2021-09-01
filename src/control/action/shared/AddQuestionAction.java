/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import control.utility.question.ServletQuestionBuilderFactory;
import control.utility.question.builder.ServletQuestionBuilder;
import entity.account.Account;
import entity.account.Administrator;
import entity.account.DyscalculiaPatient;
import entity.question.MultipleChoicesQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ChoiceModel;
import model.QuestionModel;
import model.QuestionSectionModel;
import model.QuestionTypeModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class AddQuestionAction extends Action {

    private static final String INVALID_REQUEST_MESSAGE = "Malformed request";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    
    private Gson gson;
    
    private QuestionModel questionModel;
    private QuestionTypeModel questionTypeModel;
    private QuestionSectionModel questionSectionModel;
    private ChoiceModel choiceModel;
    
    public AddQuestionAction() {
    	gson = GsonProducer.getGson();
    	
    	questionModel = new QuestionModel();
    	questionTypeModel = new QuestionTypeModel();
    	questionSectionModel = new QuestionSectionModel();
    }
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        
        int questionSectionID = Integer.parseInt(request.getParameter("questionSectionID"));
        
        QuestionSection questionSection = questionSectionModel.doRetrieveById(questionSectionID);
        if(questionSection == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || loggedAccount instanceof DyscalculiaPatient || (!(loggedAccount instanceof Administrator) && questionSection.getQuiz().isTrusted()) ) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        ServletQuestionBuilder questionBuilder = ServletQuestionBuilderFactory.retrieveQuestionBuilder(questionSection);
        
        Question question = null;
        try {
            question = questionBuilder.retrieveQuestion(request, response);
        } catch(Exception e) {
            jsonResponse = new JsonResponse(false, e.getMessage());
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        questionModel.doSave(question);
        
        jsonResponse = new JsonResponse(true);
        jsonResponse.addContent("question", question);
        if(question instanceof MultipleChoicesQuestion) {
            MultipleChoicesQuestion multipleChoiceQuestion = (MultipleChoicesQuestion) question;
            jsonResponse.addContent("choices", multipleChoiceQuestion.getChoices());
        }
        
        writer.println(gson.toJson(jsonResponse));
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String questionSectionID = request.getParameter("questionSectionID");
        String questionText = request.getParameter("questionText");
        
        try {
            Integer.parseInt(questionSectionID);
            
            if(questionText == null || questionText.equals(""))
                return false;
            
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
        
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse(false, INVALID_REQUEST_MESSAGE);
        PrintWriter writer = response.getWriter();
        
        writer.println(gson.toJson(jsonResponse));
    }
    
    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
}
