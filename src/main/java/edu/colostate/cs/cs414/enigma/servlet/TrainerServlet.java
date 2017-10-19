package edu.colostate.cs.cs414.enigma.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.colostate.cs.cs414.enigma.handler.CustomerHandler;
import edu.colostate.cs.cs414.enigma.handler.HealthInsuranceHandler;
import edu.colostate.cs.cs414.enigma.handler.MembershipHandler;
import edu.colostate.cs.cs414.enigma.handler.TrainerHandler;

/**
 * Servlet implementation class TrainerServlet
 */
@WebServlet(
	description = "Gym system login page", 
	urlPatterns = { 
			"/Trainer/ui", 
			"/trainer/ui"}
)
public class TrainerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/trainer/trainer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> values = new HashMap<String, String>();
		
		// Get the trainer post message type
		String type = request.getParameter("type");
		switch(type) {
		case "getCustomers":
			try {
				values.put("customers", new Gson().toJson(TrainerHandler.getAllCustomers()));
				values.put("rc", "0");
			} catch(Exception e) {
				values.put("rc", "1");
				values.put("msg", e.toString());
			}
			break;
			
		case "getHealthInsurances":
			try {
				HealthInsuranceHandler healthInsuranceHandler = new HealthInsuranceHandler();
				values.put("healthInsurances", new Gson().toJson(healthInsuranceHandler.getHealthInsurances()));
				values.put("rc", "0");
				healthInsuranceHandler.close();
			} catch(Exception e) {
				values.put("rc", "1");
				values.put("msg", e.toString());
			}
			break;
			
		case "getMembershipStatus":
			try {
				MembershipHandler membershipHandler = new MembershipHandler();
				values.put("membershipStatus", new Gson().toJson(membershipHandler.getMembershipStatus()));
				values.put("rc", "0");
				membershipHandler.close();
			} catch(Exception e) {
				values.put("rc", "1");
				values.put("msg", e.toString());
			}
			break;
			
		case "createNewCustomer":
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phoneNumber = request.getParameter("phoneNumber");
			String email = request.getParameter("email");
			String healthInsurance = request.getParameter("healthInsurance");
			String membershipStatus = request.getParameter("membershipStatus");
			
			try {
				TrainerHandler.createNewCustomer(firstName, lastName, phoneNumber, email, healthInsurance, membershipStatus);
			} catch(Exception e) {
				values.put("rc", "1");
				values.put("msg", e.toString());
			}
			break;
			
		default:
			values.put("rc", "1");
			values.put("msg", "Unknown request");
		}
		
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(values));
	}

}
