/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 * 
 * The Action interface follow the Strategy pattern. It is an interface type which do the work based on the passed-in arguments of the abstract method.
 */
public abstract class Action {
    
    public static final String HEADER_NAME = "RESPONSE_TYPE";
    public static final String JSON_RESPONSE = "JSON";
    public static final String FILE_RESPONSE = "FILE_RESPONSE";
    public static final String REDIRECT_RESPONSE = "Redirect";
    public static final String FORWARD_RESPONSE = "Forward";
    public static final String INVALID_RESPONSE = "InvalidResponse";
    
    /**
     * Perform the action based on the request.
     * 
     * @param request servlet request 
     * @param response servlet response
     * @return the path of the view
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     * @throws ParseException if an error occurs while parsing
     */
    public final String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        
        String responseType = this.getResponseType();
        
        switch(responseType) {
            case Action.JSON_RESPONSE:
            case Action.FILE_RESPONSE:
            case Action.REDIRECT_RESPONSE:
            case Action.FORWARD_RESPONSE:
                response.setHeader(HEADER_NAME, responseType);
                break;
            default:
                throw new IllegalArgumentException("Invalid response type");
        }
        
        if(!this.validateRequest(request, response)) {
            response.setHeader(HEADER_NAME, Action.INVALID_RESPONSE);
            catchInvalidRequestError(response);
            return null;
        }
        
        String toReturn = this.processRequest(request, response);
        
        onPreDestroy();
        
        return toReturn;
    }

	/**
     * Perform the logic of the action.
     * 
     * @param request servlet request
     * @param response servlet response
     * @return the path of the view
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     * @throws ParseException if an error occurs while parsing
     */
    protected abstract String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException;
    
    /**
     * Check if the request is valid
     * 
     * @param request servlet request
     * @param response servlet response
     * @return true if the request is correctly formed, false otherwise.
     */
    protected abstract boolean validateRequest(HttpServletRequest request, HttpServletResponse response);
    
    /**
     * Called when the request is marked as invalid.
     * Usefull if you want to notify to the user 
     * 
     * @param response servlet response
     * @throws java.io.IOException if an I/O error occurs
     */
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
        
    /**
     * Used for set the responseType
     * It's return value must be a const string of Action's class
     * i.e.
     * <pre>
     * {@code 
     *  return Action.JSON_RESPONSE
     * }
     * </pre>
     * @return the value of responseType
     */
    protected abstract String getResponseType();
    
    protected void onPreDestroy() { }
}
