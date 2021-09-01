/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.action.admin;

import control.action.Action;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InstituteModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ManageInstituteAction extends Action {

    private InstituteModel instituteModel;
    
    public ManageInstituteAction() {
		instituteModel = new InstituteModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        
        request.setAttribute("institutes", instituteModel.doRetrieveAll());
        
        return "protected/institutelist";
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
