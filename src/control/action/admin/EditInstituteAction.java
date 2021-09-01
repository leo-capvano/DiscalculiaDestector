/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.action.admin;

import com.google.gson.Gson;
import control.action.Action;
import entity.Institute;
import entity.account.Account;
import entity.account.Administrator;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.InstituteModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class EditInstituteAction extends Action {
    
    private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request";
    private static final String UNAUTHORIZED_MESSAGE = "Non ti e' permessa eseguire questa operazione";
    
    private Gson gson;
    
    private InstituteModel instituteModel;
    
    public EditInstituteAction() {
		gson = GsonProducer.getGson();
		instituteModel = new InstituteModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        
        Account loggedAccount = (Account) session.getAttribute("account");
        if(loggedAccount == null || !(loggedAccount instanceof Administrator)) {
            jsonResponse = new JsonResponse(false, UNAUTHORIZED_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }
        
        int id = Integer.parseInt(request.getParameter("instituteID"));
        String name = request.getParameter("name");
        
        Institute institute = instituteModel.doRetrieveById(id);
        if(institute == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        if(!name.equals(institute.getName())) {
            institute.setName(name);
            instituteModel.doUpdate(institute);
        }
        
        jsonResponse = new JsonResponse(true);
        writer.println(gson.toJson(jsonResponse));
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("instituteID");
        String name = request.getParameter("name");
        
        try {
            Integer.parseInt(id);
        } catch(NumberFormatException e) {
            return false;
        }
        
        return !(name == null || name.equals(""));
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

}
