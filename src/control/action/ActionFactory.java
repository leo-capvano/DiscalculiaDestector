/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action;

import control.action.admin.*;
import control.action.shared.*;
import control.action.teacher.*;
import control.action.user.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ActionFactory {

    public static Action getAction(HttpServletRequest request) {
        return ActionFactory.retrieveAction(request.getMethod() + request.getPathInfo());
    }
    
    private static Action retrieveAction(String actionKey) {
        switch(actionKey) {
        	//ForwardResponseActions
        	case "GET/quizSections":
        		return new QuizSectionAction();
        	case "GET/quizDetails":
        		return new QuizDetailAction();
        	case "GET/questionsList":
        		return new QuestionListAction();
        	case "GET/confirm-account":
        		return new ConfirmAccountAction();
        	case "GET/userlist":
        		return new UserListAction();
        	case "GET/quizstatistics":
        		return new RetrievePatientQuizAction();
        	case "GET/statisticdetail":
        		return new StatisticDetailsAction();
        	case "GET/manageinstitutes":
        		return new ManageInstituteAction();
        	case "GET/manageusers":
        		return new ManageUserAction();
        		
    		//RedirectResponseActions
        	case "GET/logout":
        		return new LogoutAction();
        		
    		//JsonResponseActions
        	case "POST/admin/signup":
        		return new AdminSignupAction();
        	case "POST/admin/addQuizSection":
        		return new AddQuizSectionAction();
        	case "POST/admin/deleteQuizSection":
        		return new DeleteSectionAction();
        	case "POST/teacher/signup":
        		return new TeacherSignupAction();
        	case "POST/addQuiz":
        		return new AddQuizAction();
        	case "POST/editQuiz":
        		return new EditQuizAction();
        	case "POST/deleteQuiz":
        		return new DeleteQuizAction();
        	case "POST/addQuestionSection":
        		return new AddQuestionSectionAction();
        	case "POST/editQuestionSection":
        		return new EditQuestionSectionAction();
        	case "POST/deleteQuestionSection":
        		return new DeleteQuestionSectionAction();
        	case "POST/addQuestion":
        		return new AddQuestionAction();
        	case "POST/deleteQuestion":
        		return new DeleteQuestionAction();
        	case "POST/user/signup":
        		return new DyscalculiaPatientSignupAction();
        	case "POST/quizLazyLoading":
        		return new RetrieveQuizAction();
        	case "POST/instituteLazyLoading":
        		return new RetrieveInstituteAction();
        	case "POST/retrieveMultiPatient":
        		return new RetrieveDyscalculiaPatientBySchoolAction();
        	case "POST/login":
        		return new LoginAction();
        	case "POST/startQuiz":
        		return new StartQuizAction();
        	case "POST/concludeQuiz":
        		return new ConcludeQuizAction();
        	case "POST/forgotPassword":
        		return new ForgotPasswordAction();
        	case "POST/addInstitute":
        		return new AddInstituteAction();
        	case "POST/editInstitute":
        		return new EditInstituteAction();
        	case "POST/switchInstituteStatus":
        		return new SwitchInstituteStatusAction();
        	case "POST/addAdmin":
        		return new AddAdminAction();
        	
        	//FileResponseAction
        	case "GET/database":
        		return new RetrieveDatabaseAction();
        	case "GET/quizSummary":
        		return new RetrieveQuizSummaryAction();
        		
    		default:
    			return null;
        }
    }
    
}
