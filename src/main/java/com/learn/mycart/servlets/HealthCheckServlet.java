package com.learn.mycart.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Simple health check endpoint for monitoring
 */
public class HealthCheckServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            // Simple health check - you can add more sophisticated checks here
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"status\":\"UP\",\"application\":\"mycart\"}");
            
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            resp.getWriter().write("{\"status\":\"DOWN\",\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
