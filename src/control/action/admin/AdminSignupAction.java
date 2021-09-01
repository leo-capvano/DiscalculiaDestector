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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 */
public class AdminSignupAction extends Action {

    private static final String ERROR_MESSAGE = "Compilare tutti i campi";
    private static final String EMAIL_ALWREADY_USED_MESSAGE = "Email gi&agrave; in uso per un altro account";
    
    private AdministratorModel administratorModel;
    
    private TeacherModel teacherModel;
    
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    
    private Gson gson;
    
    public AdminSignupAction() {
    	administratorModel = new AdministratorModel();
    	teacherModel = new TeacherModel();
    	dyscalculiaPatientModel = new DyscalculiaPatientModel();
    	gson = GsonProducer.getGson();
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

        Administrator administrator = new Administrator(name, surname, email, encryptedPassword);
        administrator.setSalt(salt);
        administrator.setActive(true);
        
        administratorModel.doSave(administrator);
        
        jsonResponse.setJsonResponseStatus(true);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        
        if(email == null || password == null || name == null || surname == null)
            return false;
        else
            return true;
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        JsonResponse jsonResponse = new JsonResponse();
        
        jsonResponse.setJsonResponseStatus(false);
        jsonResponse.setJsonResponseMessage(ERROR_MESSAGE);
        writer.println(gson.toJson(jsonResponse));
    }
    
    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }

}
