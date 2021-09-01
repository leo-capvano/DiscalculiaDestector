/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import entity.account.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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

/**
 * 
 * @author Francesco Capriglione
 * @version 1.0
 * 
 * Perform a login action
 */
public class LoginAction extends Action {

    private static final String LOGIN_FAILED_MESSAGE = "Email o password errati";
    private static final String ACCOUNT_NOT_ACTIVE_MESSAGE = "Account non verificato. Controlla la tua mail";
    private static final String INVALID_REQUEST_MESSAGE = "Inserisci email e password";
    
    private Gson gson;
    
    private AdministratorModel administratorModel;
    private TeacherModel teacherModel;
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    
    public LoginAction() {
    	administratorModel = new AdministratorModel();
		teacherModel = new TeacherModel();
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
		
		gson = GsonProducer.getGson();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Account account = administratorModel.doRetrieveByEmail(email);
        
        //If no admin account is found with given email it will search in techear account
        if(account == null) {
            account = teacherModel.doRetrieveByEmail(email);
            
            //If no teacher account is found with given email it will search in dyscalculiaPatient account
            if(account == null) {
                account = dyscalculiaPatientModel.doRetrieveByEmail(email);
                
                //If no dyscalculiaPatient account is found at this point, the email is not registered
                if(account == null) {
                    jsonResponse.setJsonResponseStatus(false);
                    jsonResponse.setJsonResponseMessage(LOGIN_FAILED_MESSAGE);

                    writer.println(gson.toJson(jsonResponse));
                    return null;
                }
            }
        }
        
        if(!account.isActive()) {
            //If the account is not active, the user can't login
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(ACCOUNT_NOT_ACTIVE_MESSAGE);
            
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        String salt = account.getSalt();
        String encryptedPassword = PasswordUtility.hashPassword(password, salt).get();
        
        if(account instanceof Administrator)
            account = administratorModel.doLogin(email, encryptedPassword);
        else if(account instanceof Teacher)
            account = teacherModel.doLogin(email, encryptedPassword);
        else if(account instanceof DyscalculiaPatient)
            account = dyscalculiaPatientModel.doLogin(email, encryptedPassword);
        
        if(account != null) {
            HttpSession session = request.getSession();
            String redirect;
            session.setAttribute("account", account);
            
            redirect = (String) session.getAttribute("redirect");
            if(redirect == null)
                redirect = "index.jsp";
            
            jsonResponse.setJsonResponseStatus(true);
            jsonResponse.addContent("redirect", redirect);
            
            writer.println(gson.toJson(jsonResponse));
        }
        else {
            jsonResponse.setJsonResponseStatus(false);
            jsonResponse.setJsonResponseMessage(LOGIN_FAILED_MESSAGE);

            writer.println(gson.toJson(jsonResponse));
        }
                
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if(email == null || email.equals("") || password == null || password.equals(""))
            return false;
        else
            return true;
    }

    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        
        jsonResponse.setJsonResponseStatus(false);
        jsonResponse.setJsonResponseMessage(INVALID_REQUEST_MESSAGE);
        writer.println(gson.toJson(jsonResponse));
    }
    
}
