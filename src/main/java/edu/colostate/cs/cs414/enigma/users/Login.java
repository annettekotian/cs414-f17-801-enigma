package edu.colostate.cs.cs414.enigma.users;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "Gym system login page", 
		urlPatterns = { 
				"/Login", 
				"/login"
		})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		// TODO Auto-generated method stub
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		Map<String, String> values = new HashMap<String, String>();
		if(User.authenticate(userName, password)) {
			User user = User.findUser(userName);
			request.getSession(false).invalidate();
			HttpSession session = request.getSession(true);
			session.setAttribute("level", user.getUserLevel().getDescription());
			
			values.put("isLoggedIn", "true");
			values.put("url", "manager.jsp");
		}
		else{
			values.put("isLoggedIn", "false");
		}
		
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(values));
	}

}
