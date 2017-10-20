package edu.colostate.cs.cs414.enigma.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.handler.AddressHandler;
import edu.colostate.cs.cs414.enigma.handler.HealthInsuranceHandler;
import edu.colostate.cs.cs414.enigma.handler.ManagerHandler;

/**
 * Servlet implementation class Manager
 */
@WebServlet({ "/manager/*" })
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/*
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String type = request.getParameter("type");
		Map<String, String> values = new HashMap<String, String>();
		PrintWriter out = response.getWriter();
		switch(type) {
		
		case "getAddManagerData":
						try {
				HealthInsuranceHandler healthInsuranceHandler = new HealthInsuranceHandler();
				values.put("healthInsurances", new Gson().toJson(healthInsuranceHandler.getHealthInsurances()));
				
				AddressHandler addHandler = new AddressHandler();
				values.put("states", new Gson().toJson(addHandler.getAllStates()));

			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			return;
			
		case "getAllTrainers" :
			try {
				List<Trainer> trainers = ManagerHandler.getAllTrainers();
				response.setContentType("application/json");
				out.write(new Gson().toJson(trainers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			
		return;
		default:
			response.sendError(404);
			break;
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String type = request.getParameter("type");
		Map<String, String> values = new HashMap<String, String>();
		PrintWriter out = response.getWriter();
		switch (type) {
		case "getHealthInsurances":
			
		
		case "createManager" :
			
			String fName = request.getParameter("fName");
			String lName = request.getParameter("lName");
			String uName = request.getParameter("uName");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String street = request.getParameter("street");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zip = request.getParameter("zip");
			String hiId = request.getParameter("hiId");
			
			
			ManagerHandler mh = new ManagerHandler();
			mh.createManager(email, fName, lName, phone, hiId, uName, password, street, city, zip,state);

			out.write("success");
			break;
		}
	}
}
