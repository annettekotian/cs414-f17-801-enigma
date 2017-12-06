package edu.colostate.cs.cs414.enigma.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Workout;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;
import edu.colostate.cs.cs414.enigma.handler.CustomerHandler;
import edu.colostate.cs.cs414.enigma.handler.TrainerHandler;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet({"/CustomerServlet/*"})
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		
		if(type.equals("getWorkoutsByCustomerId")) {
			int customerId = Integer.parseInt(request.getParameter("customerId"));
			TrainerHandler th = new TrainerHandler();
			try {
				List<Workout> wList = th.getWorkoutsByCustomerId(customerId);
				response.setContentType("application/json");
				out.write(new Gson().toJson(wList));
				return;
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
		} else if(type.equals("getCustomerInfo")) {
			Integer customerId = (Integer) request.getSession().getAttribute("customerId");
			CustomerHandler ch = new CustomerHandler();
			try {
				Customer customer = ch.getCustomerById(customerId);
				response.setContentType("application/json");
				out.write(new Gson().toJson(customer));
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		Map<String, Object> values = new HashMap<String, Object>();
		
		if(type.equals("addFeedback")) {
			int customerId = Integer.parseInt(request.getParameter("customerId"));
			int workoutId = Integer.parseInt(request.getParameter("workoutId"));
			String feedback = request.getParameter("feedback");
			
			CustomerHandler ch = new CustomerHandler();
			try {
				ch.addFeedback(customerId, workoutId, feedback);
				values.put("rc", "0");
			} catch(PersistenceException e) {
				values.put("rc", "1");
				values.put("msg", e.getCause().getCause().toString());
			} catch(IllegalArgumentException e) {
				values.put("rc", "1");
				values.put("msg", e.getMessage());
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				ch.close();
			}
			
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			
		}
	}

}
