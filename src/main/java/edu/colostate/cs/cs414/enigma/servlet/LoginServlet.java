package edu.colostate.cs.cs414.enigma.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.handler.LoginHandler;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "Gym system login page", 
		urlPatterns = { 
				"/Login", 
				"/login"
		})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get username and password from the AJAX post
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// Build a hashmap used to return information to 
		Map<String, String> values = new HashMap<String, String>();

		
		if(LoginHandler.authenticate(userName, password)) {
			HttpSession session = request.getSession(true);
			String level = LoginHandler.getUserLevel(userName);
			session.setAttribute("level", level);
			session.setAttribute("userid", LoginHandler.getUserId(userName));
			
			values.put("rc", "0");
			values.put("level", level);
			if(level.equals("ADMIN") || level.equals("MANAGER")) {
				values.put("url", "manager/ui");
				
			}
		}
		else{
			values.put("rc", "1");
			values.put("msg", "Invalid Username/Password");
		}
		
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(values));
	}

}
