/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.user;

import com.google.gson.Gson;
import control.action.Action;
import entity.account.Account;
import entity.account.DyscalculiaPatient;
import entity.enums.PatientType;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministratorModel;
import model.DyscalculiaPatientModel;
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
public class DyscalculiaPatientSignupAction extends Action {

    private static final String EMAIL_ALWREADY_USED_MESSAGE = "Email gia' in uso per un altro account";
    
    private EmailSender emailSender;
    
    private AdministratorModel administratorModel;
    private TeacherModel teacherModel;
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    
    private Gson gson;
    
    public DyscalculiaPatientSignupAction() {
		gson = GsonProducer.getGson();
		emailSender = EmailSenderFactory.getEmailSender();
		
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
		teacherModel = new TeacherModel();
		administratorModel = new AdministratorModel();
	}

    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        PrintWriter writer = response.getWriter();
        JsonResponse jsonResponse = new JsonResponse();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        
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
        
        DyscalculiaPatient patient = new DyscalculiaPatient();
        patient.setName(name);
        patient.setSurname(surname);
        patient.setEmail(email);
        patient.setPassword(encryptedPassword);
        patient.setSalt(salt);
        patient.setType(PatientType.SINGLE_PATIENT);
        patient.setEmailToken(emailToken);
        patient.setActive(false);
        
        dyscalculiaPatientModel.doSave(patient);
        patient = dyscalculiaPatientModel.doUpdate(patient);
        
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
        
        request.getSession().setAttribute("account", patient);
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        
        if(email == null || email.equals("") || password == null || password.equals("") || name == null || name.equals("") || surname == null || surname.equals(""))
            return false;
        else
            return true;
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
