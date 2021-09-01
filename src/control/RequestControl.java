/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.action.ActionFactory;
import control.action.Action;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 * 
 * This is the only servlet present in the system. 
 * Infact, the Controller part implement the Front Controller pattern (which is a specialized kind of Mediator pattern). 
 * It consist of only a single servlet which provides a centralized entry point of all requests.
 * It execute a task (called Action) based on information available by the request: the request method and the pathinfo.
 * Executing the action return the view path and sets some response header to locate the view: 
 *      - If the response header is "JSON_RESPONSE", the response is in JSON format and no redirect is required;
 *      - If the response header is "FORWARD_RESPONSE", the response must be forwarded;
 *      - If the response header is "REDIRECT_RESPONSE", the response must be redirected.
 */
@WebServlet(urlPatterns = {"/pages/*"})
public class RequestControl extends HttpServlet {

    private Logger logger = Logger.getLogger("RequestControl");
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = ActionFactory.getAction(request);
        
        if(action == null) {
            logger.info("Action not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            String view = action.execute(request, response);
            
            switch (response.getHeader(Action.HEADER_NAME)) {
                case Action.REDIRECT_RESPONSE:
                    response.sendRedirect(request.getContextPath() + "/" + view + ".jsp");
                    break;
                case Action.FORWARD_RESPONSE:
                    request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
                    break;
                case Action.JSON_RESPONSE:
                    break;
                case Action.FILE_RESPONSE:
                    break;
                case Action.INVALID_RESPONSE:
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    break;
            }
            
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Process all the action on user";
    }// </editor-fold>

}
