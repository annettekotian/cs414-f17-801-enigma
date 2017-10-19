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

import edu.colostate.cs.cs414.enigma.dao.UserDao;
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
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get username and password from the AJAX post
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// Build a hashmap used to return information to 
		//Map<String, String> values = new HashMap<String, String>();
		
		LoginHandler loginHandler = new LoginHandler();
		
		if(loginHandler.authenticate(userName, password)) {
			HttpSession session = request.getSession(true);
			String level = loginHandler.getUserLevel(userName);
			session.setAttribute("level", level);
			session.setAttribute("userid", loginHandler.getUserId(userName));
			loginHandler.close();
			request.setAttribute("level", level);
			request.getRequestDispatcher("/WEB-INF/views/manager/manager.jsp").forward(request, response);
		}
		else{
			loginHandler.close();
			request.getRequestDispatcher("/WEB-INF/views/trainer/trainer.jsp").forward(request, response);
		}
		
		
	}

}
