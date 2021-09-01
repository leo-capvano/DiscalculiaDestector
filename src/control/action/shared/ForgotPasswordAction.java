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
import entity.account.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.UUID;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
public class ForgotPasswordAction extends Action {

    private Gson gson;
    
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    private AdministratorModel administratorModel;
    private TeacherModel teacherModel;
    
    private EmailSender emailSender;
    
    public ForgotPasswordAction() {
    	gson = GsonProducer.getGson();
    	dyscalculiaPatientModel = new DyscalculiaPatientModel();
    	administratorModel = new AdministratorModel();
    	teacherModel = new TeacherModel();
    	emailSender = EmailSenderFactory.getEmailSender();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        
        String email = request.getParameter("email");
        
        Account account = administratorModel.doRetrieveByEmail(email);
        
        if(account == null) {
            account = teacherModel.doRetrieveByEmail(email);
            if(account == null) {
                account = dyscalculiaPatientModel.doRetrieveByEmail(email);
                if(account == null) {
                    jsonResponse = new JsonResponse(false, "No account found");
                    writer.println(gson.toJson(jsonResponse));
                    
                    return null;
                }
            }
        }
            
        if(!account.isActive()) {
            jsonResponse = new JsonResponse(false, "Account not active yet");
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        String newPassword = UUID.randomUUID().toString();
        int index = newPassword.indexOf('-') - 1;
        newPassword = newPassword.substring(0, index);
        
        String salt = PasswordUtility.generateSalt(256).get();
        String encryptedPassword = PasswordUtility.hashPassword(newPassword, salt).get();
        account.setSalt(salt);
        account.setPassword(encryptedPassword);
        
        if(account instanceof Administrator) {
            Administrator admin = (Administrator) account;
            administratorModel.doUpdate(admin);
        }
        else if(account instanceof Teacher) {
            Teacher teacher = (Teacher) account;
            teacherModel.doUpdate(teacher);
        }
        else if(account instanceof DyscalculiaPatient) {
            DyscalculiaPatient patient = (DyscalculiaPatient) account;
            dyscalculiaPatientModel.doUpdate(patient);
        }
        
        String message = "Ciao " + account.getName() + ", ecco la tua nuova password: " + newPassword;
        
        emailSender.sendEmail(email, "Recupero password", message);
        
        jsonResponse = new JsonResponse(true);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        
        try {
            InternetAddress.parse(email);
            return true;
        } catch(AddressException e) {
            return false;
        }
        
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
