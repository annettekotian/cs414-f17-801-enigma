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

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.handler.CustomerHandler;
import edu.colostate.cs.cs414.enigma.handler.LoginHandler;
import edu.colostate.cs.cs414.enigma.handler.ManagerHandler;
import edu.colostate.cs.cs414.enigma.handler.TrainerHandler;

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
		
		// Get username and password from the post
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// Build a hashmap used to return information to 
		Map<String, String> values = new HashMap<String, String>();
		
		LoginHandler lh = new LoginHandler();
		TrainerHandler th = new TrainerHandler();
		ManagerHandler mh = new ManagerHandler();
		try {
			if(lh.authenticate(userName, password)) {
				HttpSession session = request.getSession(true);
				String level = lh.getUserLevel(userName);
				// set level and user id in the session
				session.setAttribute("level", level);
				int id = lh.getUserId(userName);
				session.setAttribute("userid", id);
				
				// redirect based on level
				if(level.equals("TRAINER")) {
					Trainer t = th.getTrainerByUserId(id);
					request.setAttribute("trainer", new Gson().toJson(t));
					
					request.getRequestDispatcher("/WEB-INF/views/trainer/trainer.jsp").forward(request, response);
				} else if (level.equals("CUSTOMER")) {
					Customer c = new CustomerHandler().getCustomerByUserId(id);
					request.setAttribute("level", level);
					request.setAttribute("customerData", new Gson().toJson(c) );
					request.getRequestDispatcher("/WEB-INF/views/customer/customer.jsp").forward(request, response);
					
				} else {
					// get all manager data to display in the ui
					List<Manager> managers = mh.getAllManagers();			
					request.setAttribute("level", level);
					request.setAttribute("managerData", new Gson().toJson(managers));
					request.getRequestDispatcher("/WEB-INF/views/manager/manager.jsp").forward(request, response);
				}
			} else {
				response.sendRedirect("/index.jsp");
			}
		} finally {
			lh.close();
			th.close();
			mh.close();
		}	
	}
}
