/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.action.shared;

import control.action.Action;
import entity.Institute;
import entity.account.Account;
import entity.account.Administrator;
import entity.account.DyscalculiaPatient;
import entity.account.Teacher;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DyscalculiaPatientModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class UserListAction extends Action  {

    private DyscalculiaPatientModel dyscalculiaPatientModel;
    
    public UserListAction() {
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        List<DyscalculiaPatient> dyscalculiaPatients = new ArrayList<DyscalculiaPatient>();
        
        if(account instanceof Administrator)
            dyscalculiaPatients = dyscalculiaPatientModel.doRetrieveAllQuizzed();
        else if(account instanceof Teacher) {
            Teacher teacher = (Teacher) account;
            List<Institute> institutes = teacher.getInstitutes();
            
            for(Institute institute : institutes) {
                List<DyscalculiaPatient> patients = institute.getStudents();
                for(int i = 0; i < patients.size(); i++) {
                    DyscalculiaPatient patient = patients.get(i);
                    if(patient.getQuizStatistics().size() > 0)
                        dyscalculiaPatients.add(patient);
                }
            }

        }
        
        request.setAttribute("patients", dyscalculiaPatients);
        
        return "protected/userlist";
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    protected String getResponseType() {
        return Action.FORWARD_RESPONSE;
    }

}
