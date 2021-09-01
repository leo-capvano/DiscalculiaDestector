/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.user;

import com.google.gson.Gson;
import control.action.Action;
import entity.Institute;
import entity.account.DyscalculiaPatient;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DyscalculiaPatientModel;
import model.InstituteModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class RetrieveDyscalculiaPatientBySchoolAction extends Action {

    private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request";
    
    private Gson gson;
    
    private DyscalculiaPatientModel dyscalculiaPatientModel;
    private InstituteModel instituteModel;
    
    public RetrieveDyscalculiaPatientBySchoolAction() {
		gson = GsonProducer.getGson();
		
		dyscalculiaPatientModel = new DyscalculiaPatientModel();
		instituteModel = new InstituteModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse;
        PrintWriter writer = response.getWriter();
        
        int instituteID = Integer.parseInt(request.getParameter("institute"));
        String classRoomCode = request.getParameter("classRoomCode");
        String schoolRegister = request.getParameter("schoolRegister");
        
        Institute institute = instituteModel.doRetrieveById(instituteID);
        if(institute == null) {
            catchInvalidRequestError(response);
            return null;
        }
        
        DyscalculiaPatient patient = dyscalculiaPatientModel.doRetrieveBySchool(classRoomCode, institute, schoolRegister);
        
        jsonResponse = new JsonResponse(true);
        jsonResponse.addContent("patient", patient);
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String instituteID = request.getParameter("institute");
        String classRoomCode = request.getParameter("classRoomCode");
        String schoolRegister = request.getParameter("schoolRegister");
        
        if(classRoomCode == null || schoolRegister == null)
            return false;
        
        try {
            Integer.parseInt(instituteID);
        } catch(NumberFormatException e) {
            return false;
        }
        
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
    
}
