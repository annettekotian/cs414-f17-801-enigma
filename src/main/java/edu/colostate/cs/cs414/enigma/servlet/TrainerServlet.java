package edu.colostate.cs.cs414.enigma.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import edu.colostate.cs.cs414.enigma.handler.CustomerHandler;

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
		
		
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		Map<String, Object> values = new HashMap<String, Object>();
		switch(type) {
		case "getCustomers": 
			try {
				
				CustomerHandler ch = new CustomerHandler();
				List<Customer> list = ch.getCustomers();
				values.put("customers", list);
				values.put("status", "success");
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		case "getSearchCustomerResults":

			try {
				String searchText = request.getParameter("searchText");
				CustomerHandler ch = new CustomerHandler();
				List<Customer> customers = new ArrayList<Customer>();
				if (searchText.isEmpty()) {
					customers = ch.getCustomers();
				} else {
					customers = ch.getCustomerByKeyword(searchText);
				}
				values.put("results", customers);
				out.write(new Gson().toJson(values));
			} catch (Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		case "getAllExercises":
			try {
				TrainerHandler th = new TrainerHandler();
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getAllExercises()));
				th.close();
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
