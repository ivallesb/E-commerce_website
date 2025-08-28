package com.learn.mycart.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.learn.mycart.dao.UserDao;
import com.learn.mycart.entities.User;
import com.learn.mycart.helper.FactoryProvider;
import com.learn.mycart.helper.MetricsHelper;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email= request.getParameter("email");
		String password= request.getParameter("password");
		
		UserDao userDao=new UserDao(FactoryProvider.getFactory());
        User user=userDao.getUserByEmailAndPassword(email, password);
		
        HttpSession httpSession=request.getSession();
        if(user==null)
        {

        	
			httpSession.setAttribute("message","*Invalid Details !! Try with another one*");
			response.sendRedirect("login.jsp");
			return;
        }
        else {

        	// Increment login metrics for successful login
        	MetricsHelper.incrementUserLogins();
        	
        	httpSession.setAttribute("current-user",user);
        	
        	if(user.getUserType().equals("admin"))
        	{
        		//admin: admin.jsp
        		response.sendRedirect("admin.jsp");
        	}
        	else if(user.getUserType().equals("normal"))
        	{
        		//normal: normal.jsp
        		response.sendRedirect("normal.jsp");
        	}
        	else
        	{
        		//out.println("We have not identified user type")
        		
    			httpSession.setAttribute("message","We have not identified user type !!!");
    			response.sendRedirect("login.jsp");
    			return;
        	}
        	
        	
        }
		
	}

}
