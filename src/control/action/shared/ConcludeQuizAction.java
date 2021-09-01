/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import control.action.Action;
import control.utility.dto.QuizAnswerDTO;
import entity.account.DyscalculiaPatient;
import entity.QuizStatistics;
import entity.StatisticRow;
import entity.question.Question;
import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuestionType;
import entity.quiz.QuizFeedback;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.QuestionModel;
import model.QuizFeedbackModel;
import model.QuizStatisticsModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ConcludeQuizAction extends Action {

    private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request";
    
    private Gson gson;
    
    private QuestionModel questionModel;
    private QuizStatisticsModel quizStatisticsModel;
    private QuizFeedbackModel feedbackModel;
    
    public ConcludeQuizAction() {
    	gson = GsonProducer.getGson();
    	
    	questionModel = new QuestionModel();
    	quizStatisticsModel = new QuizStatisticsModel();
    	feedbackModel = new QuizFeedbackModel();
    }
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        
        QuizAnswerDTO[] patientAnswers;
        QuizFeedback quizFeedback;
        DyscalculiaQuiz quiz = null;
        
        String quizData = request.getParameter("quizData");
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(quizData).getAsJsonObject();
        String answersList = jsonObject.get("answersList").toString();
        String feedback = jsonObject.get("feedback").toString();
        
        patientAnswers = gson.fromJson(answersList, QuizAnswerDTO[].class);
        quizFeedback = gson.fromJson(feedback, QuizFeedback.class);
        
        Date now = new Date(System.currentTimeMillis());
        
        QuizStatistics quizStatistics = new QuizStatistics();
        quizStatistics.setDate(now);
        quizFeedback.setDate(now);
        
        //Key = questionTypeID, value = statisticRow of the question type with ID = questionTypeID
        Map<Integer, StatisticRow> statisticsMap = new HashMap<Integer, StatisticRow>();
        //Key = questionTypeID, value = number of questions for the question type with ID = questionTypeID
        Map<Integer, Integer> numOfQuestionForTypeMap = new HashMap<Integer, Integer>();
        //Key = questionTypeID, value = seconds ellapsed for the question type with ID = questionTypeID
        Map<Integer, Integer> secondsEllapsedForTypeMap = new HashMap<Integer, Integer>();
        //Key = questionID, value = patient's answer for the question with ID = questionID
        Map<Integer, QuizAnswerDTO> questionAnswerMap = new HashMap<Integer, QuizAnswerDTO>();
        
        int totalSeconds = 0;
        int totalWrong = 0;
        int totalCorrect = 0;
        int totalSkipped = 0;
        
        for(int i = 0; i < patientAnswers.length; i++) {
            //Collect information and put it in the maps
            QuizAnswerDTO currentAnswer = patientAnswers[i];
            
            Question question = questionModel.doRetrieveById(currentAnswer.getQuestionID());
            if(question == null) {
                catchInvalidRequestError(response);
                return null;
            }
            
            if(quiz == null) {
                //Executed only one time
                quiz = question.getQuestionSection().getQuiz();
            }
            
            questionAnswerMap.put(question.getId(), currentAnswer);
            
            QuestionType questionType = question.getQuestionType();
            
            StatisticRow statisticRow = statisticsMap.get(questionType.getId());
            Integer numOfQuestion = numOfQuestionForTypeMap.get(questionType.getId());
            Integer secondsEllapsed = secondsEllapsedForTypeMap.get(questionType.getId());
            
            if(statisticRow == null) {
                statisticRow = new StatisticRow();
                statisticRow.setQuestionType(questionType);
                statisticRow.setQuizStatistics(quizStatistics);
            }
            
            if(numOfQuestion == null)
                numOfQuestion = 0;
            if(secondsEllapsed == null)
                secondsEllapsed = 0;
            
            if(currentAnswer.isSkipped()) {
                statisticRow.setSkippedAnswers(statisticRow.getSkippedAnswers() + 1);
                totalSkipped++;
            }
            else {
                
                boolean isCorrect = false;
                
                if(questionType.getId() == 12 || questionType.getId() == 6) {
                    //Question with correctAnswerText
                    if(question.getCorrectAnswerText().equalsIgnoreCase(currentAnswer.getAnswerText()))
                        isCorrect = true;
                }
                else {
                    if(question.getCorrectAnswer().equals(currentAnswer.getAnswer())) { 
                        if(question.getCorrectAnswerDenominator() != null) {
                            if(question.getCorrectAnswerDenominator().equals(currentAnswer.getAnswerDenominator()))
                                isCorrect = true;
                        }
                        else
                            isCorrect = true;
                    }
                        
                }
                
                if(isCorrect) {
                    statisticRow.setCorrectAnswers(statisticRow.getCorrectAnswers() + 1);
                    totalCorrect++;
                }
                else {
                    statisticRow.setWrongAnswers(statisticRow.getWrongAnswers() + 1);
                    totalWrong++;
                }
                
                currentAnswer.setIsCorrect(isCorrect);
            }
            
            secondsEllapsed += currentAnswer.getSeconds();
            totalSeconds += currentAnswer.getSeconds();
            
            numOfQuestion++;
            
            statisticsMap.put(questionType.getId(), statisticRow);
            secondsEllapsedForTypeMap.put(questionType.getId(), secondsEllapsed);
            numOfQuestionForTypeMap.put(questionType.getId(), numOfQuestion);
        }
        
        quizStatistics.setSecondsEllapsed(totalSeconds);
        quizStatistics.setTotalCorrectAnswers(totalCorrect);
        quizStatistics.setTotalSkippedAnswers(totalSkipped);
        quizStatistics.setTotalWrongAnswers(totalWrong);
        
        List<StatisticRow> statisticRows = new ArrayList<StatisticRow>();
        
        for(Map.Entry<Integer, StatisticRow> entry : statisticsMap.entrySet()) {
            int questionTypeId = entry.getKey();
            StatisticRow statisticRow = entry.getValue();
            
            int secondsEllapsed = secondsEllapsedForTypeMap.get(questionTypeId);
            int totalAnswer = numOfQuestionForTypeMap.get(questionTypeId);
            
            statisticRow.setAverageTime(secondsEllapsed/totalAnswer);
            statisticRows.add(statisticRow);
        }
        
        quizStatistics.setStatisticRows(statisticRows);
        
        Object debugMode = session.getAttribute("debugMode");
        
        if(debugMode == null || (!(boolean) debugMode)) {
            DyscalculiaPatient patient = (DyscalculiaPatient) session.getAttribute("accountInQuiz");
            quizStatistics.setPatient(patient);
            
            //Calculate performance
            double precision = 0;
            double recall;
            double performance;
            
            int answeredQuestionsCount = patientAnswers.length - totalSkipped;
            
            //The precision is calculated dividing correct answer by answered questions
            if(answeredQuestionsCount > 0)
                precision = totalCorrect / (double) answeredQuestionsCount;
            
            //The recall is calculated dividing correct answer by the number of questions
            recall = totalCorrect / (double) patientAnswers.length;
            
            performance = (2.0 * precision * recall) / (precision + recall);
            
            quizStatistics.setPerformance(performance);
            quizStatistics.setQuiz(quiz);
            
            quizFeedback.setPatient(patient);
            quizFeedback.setQuiz(quiz);
            
            quizStatisticsModel.doSave(quizStatistics);
            feedbackModel.doSave(quizFeedback);
        }
        
        jsonResponse = new JsonResponse(true);
        jsonResponse.addContent("redirect", "quizResults.jsp");
        writer.println(gson.toJson(jsonResponse));
        
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        String quizName = quiz.getName();
        
        session.setAttribute("isValid", true);
        session.setAttribute("questionNumber", questions.size());
        session.setAttribute("quizStatistics", quizStatistics);
        session.setAttribute("questions", questions);
        session.setAttribute("quizName", quizName);
        session.setAttribute("questionAnswerMap", questionAnswerMap);
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String quizData = request.getParameter("quizData");
        if(quizData == null || quizData.equals(""))
            return false;
        
        JsonObject jsonObject;
        JsonParser parser = new JsonParser();
        
        try {
            jsonObject = parser.parse(quizData).getAsJsonObject();
            String answersList = jsonObject.get("answersList").toString();
            String feedback = jsonObject.get("feedback").toString();
            
            gson.fromJson(answersList, QuizAnswerDTO[].class);
            gson.fromJson(feedback, QuizFeedback.class);
        } catch (JsonSyntaxException | NullPointerException e) {
            return false;
        }
        
        return true;
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse(false, MALFORMED_REQUEST_MESSAGE);
        PrintWriter writer = response.getWriter();
        
        writer.println(gson.toJson(jsonResponse));
    }

    
    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
}
