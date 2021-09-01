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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministratorModel;
import model.ChoiceModel;
import model.DyscalculiaPatientModel;
import model.DyscalculiaQuizModel;
import model.InstituteModel;
import model.QuestionModel;
import model.QuestionSectionModel;
import model.QuestionTypeModel;
import model.QuizFeedbackModel;
import model.QuizSectionModel;
import model.QuizStatisticsModel;
import model.TeacherInstituteModel;
import model.TeacherModel;
import utility.GsonProducer;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class RetrieveDatabaseAction extends Action {
    

    private AdministratorModel adminModel;
    private ChoiceModel choiceModel;
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    private InstituteModel instituteModel;
    private QuestionModel questionModel;
    private QuestionSectionModel questionSectionModel;
    private QuestionTypeModel questionTypeModel;
    private QuizFeedbackModel quizFeedbackModel;
    private QuizSectionModel quizSectionModel;
    private QuizStatisticsModel quizStatisticsModel;
    private TeacherInstituteModel teacherInstituteModel;
    private TeacherModel teacherModel;
    
    private Gson gson;
    
    private static final String FILE_CONTENT_TYPE = "application/json";
    private static final String FILE_NAME = "database.json";
    
    public RetrieveDatabaseAction() {
		adminModel = new AdministratorModel();
		choiceModel = new ChoiceModel();
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
		dyscalculiaQuizModel = new DyscalculiaQuizModel();
		instituteModel = new InstituteModel();
		questionModel = new QuestionModel();
		questionSectionModel = new QuestionSectionModel();
		questionTypeModel = new QuestionTypeModel();
		quizFeedbackModel = new QuizFeedbackModel();
		quizSectionModel = new QuizSectionModel();
		quizStatisticsModel = new QuizStatisticsModel();
		teacherInstituteModel = new TeacherInstituteModel();
		teacherModel = new TeacherModel();
		
		gson = GsonProducer.getGson();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        response.setContentType(FILE_CONTENT_TYPE);
        response.setHeader("Content-Disposition","attachment; filename=" + FILE_NAME);
        
        PrintWriter writer = response.getWriter();
        Map<String, Object> toPrint = new HashMap<String, Object>();
        
        //Retrieving all
        toPrint.put("admins", adminModel.doRetrieveAll());
        toPrint.put("choices", choiceModel.doRetrieveAll());
        toPrint.put("dyscalculiaPatients", dyscalculiaPatientModel.doRetrieveAll());
        toPrint.put("dyscalculiaQuizzes", dyscalculiaQuizModel.doRetrieveAll());
        toPrint.put("institutes", instituteModel.doRetrieveAll());
        toPrint.put("questions", questionModel.doRetrieveAll());
        toPrint.put("questionSections", questionSectionModel.doRetrieveAll());
        toPrint.put("questionTypes", questionTypeModel.doRetrieveAll());
        toPrint.put("quizFeedbacks", quizFeedbackModel.doRetrieveAll());
        toPrint.put("quizSections", quizSectionModel.doRetrieveAll());
        toPrint.put("quizStatistics", quizStatisticsModel.doRetrieveAll());
        toPrint.put("teacherInstitutes", teacherInstituteModel.doRetrieveAll());
        toPrint.put("teacherModels", teacherModel.doRetrieveAll());
        
        writer.println(gson.toJson(toPrint));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    protected String getResponseType() {
        return Action.FILE_RESPONSE;
    }

}
