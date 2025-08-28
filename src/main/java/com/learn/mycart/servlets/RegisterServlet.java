package com.learn.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learn.mycart.entities.User;
import com.learn.mycart.helper.FactoryProvider;
import com.learn.mycart.helper.MetricsHelper;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			
			String userName= request.getParameter("user_name");
			String userEmail= request.getParameter("user_email");
			String userPassword= request.getParameter("user_password");
			String userPhone= request.getParameter("user_phone");
			String userAddress= request.getParameter("user_address");
			
			//creating user object to store edata
			User user=new User(userName, userEmail, userPassword, userPhone, "default.jpg", userAddress,"normal");
			
			Session hibernateSession=FactoryProvider.getFactory().openSession();
			
			Transaction tx= hibernateSession.beginTransaction();
			
			int userId=(Integer) hibernateSession.save(user);
			
			
			tx.commit();
			hibernateSession.close();
			
			// Increment metrics for successful registration
			MetricsHelper.incrementUserRegistrations();
			
			HttpSession httpSession=request.getSession();
			httpSession.setAttribute("message","*Registration SuccessFul !! User id is:" + userId);
			response.sendRedirect("register.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
