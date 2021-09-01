/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.action.shared;

import control.action.Action;
import entity.account.Account;
import entity.account.Administrator;
import entity.account.DyscalculiaPatient;
import entity.account.Teacher;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministratorModel;
import model.DyscalculiaPatientModel;
import model.TeacherModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ConfirmAccountAction extends Action {

    private AdministratorModel administratorModel;
    private TeacherModel teacherModel;
    private DyscalculiaPatientModel patientModel;
    
    public ConfirmAccountAction() {
    	administratorModel = new AdministratorModel();
    	teacherModel = new TeacherModel();
    	patientModel = new DyscalculiaPatientModel();
    }
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        String emailToken = request.getParameter("token");
        Account toActivate = null;
        
        toActivate = administratorModel.doRetrieveByEmailToken(emailToken);
        if(toActivate == null) {
            toActivate = teacherModel.doRetrieveByEmailToken(emailToken);
            if(toActivate == null) {
                //If the token is not into teacher table
                toActivate = patientModel.doRetrieveByEmailToken(emailToken);
                if(toActivate == null) {
                    //If the token is not into patient table
                    this.catchInvalidRequestError(response);
                    return "protected/account-activated";
                }
            }
        }
        
        toActivate.setEmailToken(null);
        toActivate.setActive(true);
        
        if(toActivate instanceof Administrator)
            administratorModel.doUpdate((Administrator) toActivate);
        else if(toActivate instanceof Teacher)
            teacherModel.doUpdate((Teacher) toActivate);
        else
            patientModel.doUpdate((DyscalculiaPatient) toActivate);
        
        return "protected/account-activated";
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String emailToken = request.getParameter("token");
        return emailToken != null;
    }

    @Override
    protected String getResponseType() {
        return Action.FORWARD_RESPONSE;
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        response.setHeader(HEADER_NAME, Action.INVALID_RESPONSE);
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
