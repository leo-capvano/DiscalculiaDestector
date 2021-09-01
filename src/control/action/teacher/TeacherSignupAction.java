/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.teacher;

import com.google.gson.Gson;
import control.action.Action;
import entity.Institute;
import entity.TeacherInstitute;
import entity.account.Account;
import entity.account.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministratorModel;
import model.DyscalculiaPatientModel;
import model.InstituteModel;
import model.TeacherInstituteModel;
import model.TeacherModel;
import utility.GsonProducer;
import utility.JsonResponse;
import utility.PasswordUtility;
import utility.email.EmailSender;
import utility.email.EmailSenderFactory;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class TeacherSignupAction extends Action {

    private static final String EMAIL_ALWREADY_USED_MESSAGE = "Email gia' in uso per un altro account";
    private static final String EMAIL_WRONG_FORMAT_MESSAGE = "Inserire una mail valida";
    private static final String EMAIL_NOT_VALID_MESSAGE = "L'Email deve essere di tipo istituzionale (@istruzione.it)";
    
    private EmailSender emailSender;
    private Gson gson;
    
    private InstituteModel instituteModel;
    private AdministratorModel administratorModel;
    private TeacherModel teacherModel;
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    private TeacherInstituteModel teacherInstituteModel;
    
    public TeacherSignupAction() {
    	emailSender = EmailSenderFactory.getEmailSender();
    	gson = GsonProducer.getGson();
    	
		instituteModel = new InstituteModel();
		administratorModel = new AdministratorModel();
		teacherModel = new TeacherModel();
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
		teacherInstituteModel = new TeacherInstituteModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        PrintWriter writer = response.getWriter();
        JsonResponse jsonResponse = new JsonResponse();
        
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int instituteID = Integer.parseInt(request.getParameter("institute"));
        
        int atIndex = email.indexOf('@');
        if(atIndex == -1) {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(EMAIL_WRONG_FORMAT_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        String domain = email.substring(atIndex);
        if(!domain.equals("@istruzione.it")) {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(EMAIL_NOT_VALID_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        Institute institute = instituteModel.doRetrieveById(instituteID);
        
        if(institute == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        Account account = administratorModel.doRetrieveByEmail(email);
        
        if(account != null) {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(EMAIL_ALWREADY_USED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        account = teacherModel.doRetrieveByEmail(email);
            
        if(account != null) {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(EMAIL_ALWREADY_USED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        account = dyscalculiaPatientModel.doRetrieveByEmail(email);
            
        if(account != null) {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(EMAIL_ALWREADY_USED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        String salt = PasswordUtility.generateSalt(256).get();
        String encryptedPassword = PasswordUtility.hashPassword(password, salt).get();
        String emailToken = UUID.randomUUID().toString();
        
        Teacher toSave = new Teacher(name, surname, email, encryptedPassword);
        toSave.setSalt(salt);
        toSave.setActive(false);
        toSave.setEmailToken(emailToken);
        
        teacherModel.doSave(toSave);
        toSave = teacherModel.doUpdate(toSave);
        
        TeacherInstitute teacherInstitute = new TeacherInstitute(toSave, institute);
        teacherInstituteModel.doSave(teacherInstitute);
        
        String host = request.getServerName();
        int port = request.getServerPort();
        if(port != 80)
            host += ":" + port;
        
        String contextPath = request.getServletContext().getContextPath();
        if(!contextPath.equals(""))
        	host += contextPath;
        
        String activationURL = host + "/pages/confirm-account?token=" + emailToken;
        
        emailSender.sendEmail(email, "Verifica il tuo account", "Benvenuto!\n Procedi all'attivazione del tuo account a questo link:\n\n" + activationURL);
        
        jsonResponse.setJsonResponseStatus(true);
        writer.println(gson.toJson(jsonResponse));
        
        request.getSession().setAttribute("account", toSave);
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String instituteID = request.getParameter("institute");
        
        if(name == null || name.equals("") || surname == null || surname.equals("") || email == null || email.equals("") || password == null || password.equals(""))
            return false;
        
        try {
            Integer.parseInt(instituteID);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        JsonResponse jsonResponse = new JsonResponse();
        
        jsonResponse.setJsonResponseStatus(false);
        jsonResponse.setJsonResponseMessage("Malformed Request");
        writer.println(gson.toJson(jsonResponse));
    }
    
    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
    @Override
    protected void onPreDestroy() {
    	EmailSenderFactory.releaseEmailSender(emailSender);
    }
    
}
