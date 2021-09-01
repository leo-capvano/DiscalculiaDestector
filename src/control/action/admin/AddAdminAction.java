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
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class AddAdminAction extends Action {
    
    private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    private static final String EMAIL_ALWREADY_USED_MESSAGE = "Email gia' in uso per un altro account";
    
    private Gson gson;
    
    private AdministratorModel administratorModel;
    
    private TeacherModel teacherModel;
    
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    
    private EmailSender emailSender;
    
    public AddAdminAction() {
    	this.gson = GsonProducer.getGson();
    	this.administratorModel = new AdministratorModel();
    	this.teacherModel = new TeacherModel();
    	this.dyscalculiaPatientModel = new DyscalculiaPatientModel();
    	this.emailSender = EmailSenderFactory.getEmailSender();
    }
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || !(loggedAccount instanceof Administrator)) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        String email = request.getParameter("email");
        
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
        
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        
        //Generating a random password
        String password = UUID.randomUUID().toString();
        int index = password.indexOf('-') - 1;
        password = password.substring(0, index);
        //Encrypting the password
        String salt = PasswordUtility.generateSalt(256).get();
        String encryptedPassword = PasswordUtility.hashPassword(password, salt).get();
        
        String emailToken = UUID.randomUUID().toString();
        
        Administrator toSave = new Administrator(name, surname, email, encryptedPassword);
        toSave.setSalt(salt);
        toSave.setActive(false);
        toSave.setEmailToken(emailToken);
        
        administratorModel.doSave(toSave);
        
        String host = request.getServerName();
        
        int port = request.getServerPort();
        if(port != 80)
            host += ":" + port;
        
        String contextPath = request.getServletContext().getContextPath();
        if(!contextPath.equals(""))
        	host += contextPath;
        
        String activationURL = host + "/pages/confirm-account?token=" + emailToken;
        
        String message = "Benvenuto " + name + "!\n"
                + "Sei stato nominato clinico del sistema. "
                + "Puoi accedere al sistema con questa mail e con password " + password + " dopo aver attivato l'account a questo link:\n\n" + activationURL;
        
        emailSender.sendEmail(email, "Verifica il tuo account", message);
        
        jsonResponse.setJsonResponseStatus(true);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        
        if(email == null || email.equals(""))
            return false;
        
        if(name == null || name.equals(""))
            return false;
        
        if(surname == null || name.equals(""))
            return false;
        
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

	@Override
	protected void onPreDestroy() {
		EmailSenderFactory.releaseEmailSender(emailSender);
	}

}
