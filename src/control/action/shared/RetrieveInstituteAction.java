/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import entity.Institute;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InstituteModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class RetrieveInstituteAction extends Action {

    private Gson gson;
    
    private InstituteModel instituteModel;
    
    public RetrieveInstituteAction() {
		gson = GsonProducer.getGson();
		
		instituteModel = new InstituteModel();
	}

    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter writer = response.getWriter();
        
        List<Institute> allInstitutes = instituteModel.doRetrieveAll();
        List<Institute> activeInstitutes = new ArrayList<Institute>();
        
        for(Institute institute : allInstitutes)
            if(institute.isActive())
                activeInstitutes.add(institute);
        
        jsonResponse.setJsonResponseStatus(true);
        jsonResponse.addContent("institutes", activeInstitutes);
        
        writer.println(gson.toJson(jsonResponse));
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }
    
}
