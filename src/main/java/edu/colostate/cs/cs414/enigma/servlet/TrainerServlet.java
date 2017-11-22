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
import com.google.gson.reflect.TypeToken;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Workout;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;
import edu.colostate.cs.cs414.enigma.handler.CustomerHandler;
import edu.colostate.cs.cs414.enigma.handler.SystemHandler;
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
		
		
		if(type.equals("getCustomers")) {
			CustomerHandler ch = new CustomerHandler();
			try {
				List<Customer> list = ch.getCustomers();
				values.put("customers", list);
				values.put("status", "success");
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			return;
			
		} else if(type.equals("getSearchCustomerResults")) {
			String searchText = request.getParameter("searchText");
			CustomerHandler ch = new CustomerHandler();
			try {
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
			} finally {
				ch.close();
			}
			return;
			
		} else if(type.equals("getAllExercises")) {
			TrainerHandler th = new TrainerHandler();
			try {
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getAllExercises()));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			return;
			
		} else if(type.equals("getAllMachines")) {
			SystemHandler sh = new SystemHandler();
			try {
				response.setContentType("application/json");
				out.write(new Gson().toJson(sh.getInventory()));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sh.close();
			}
			return;
			
		} else if(type.equals("searchExercises")) {
			String value = request.getParameter("value");
			TrainerHandler th = new TrainerHandler();
			try {
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.searchExercises(value)));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			return;
			
		} else if(type.equals("getAllWorkouts")) {
			TrainerHandler th = new TrainerHandler();
			try {
				List<Workout> wList = th.getAllWorkouts();
				response.setContentType("application/json");
				out.write(new Gson().toJson(wList));
				return;
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				//System.out.println("closed connection");
				th.close();
			}

			
		}  else if(type.equals("getSearchWorkoutResults")) {
			TrainerHandler th = new TrainerHandler();
			try {
				List<Workout> wList = null;
				String keyword = request.getParameter("searchText");
				if(keyword.isEmpty()) {
					wList = th.getAllWorkouts();
				} else {
					wList = th.searchWorkouts(keyword);
				}
				response.setContentType("application/json");
				out.write(new Gson().toJson(wList));
				return;
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			
		} else if(type.equals("getWorkoutsByCustomerId")) {
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
				//System.out.println("closed connection");
				th.close();
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
		
		if(type.equals("createExercise") || type.equals("modifyExercise")) {
			int exerciseId = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int machineId = Integer.parseInt(request.getParameter("machineId"));
			int hours = Integer.parseInt(request.getParameter("hours"));
			int minutes = Integer.parseInt(request.getParameter("minutes"));
			int seconds = Integer.parseInt(request.getParameter("seconds"));
			String[] sets = request.getParameterValues("sets[]");
			List<Integer> repetitions = new ArrayList<Integer>();
			if(sets != null) {
				for(int i=0; i<sets.length; i++) {
					repetitions.add(Integer.parseInt(sets[i]));
				}
			}
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				if(type.equals("createExercise")) {
					th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
				} else if(type.equals("modifyExercise")) {
					th.updateExercise(exerciseId, name, machineId, hours, minutes, seconds, repetitions);
				}
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(ExerciseDurationException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(ExerciseSetException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(ExerciseException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			
		} else if(type.equals("deleteExercise")) {
			int exerciseId = Integer.parseInt(request.getParameter("id"));
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteExercise(exerciseId);
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			
		} else if (type.equals("createWorkout")) {
			
			String name = request.getParameter("name");
			String[] exerciseList = request.getParameterValues("exerciseList[]");
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				
				Workout w = th.createWorkout(name, exerciseList);
				
				response.setContentType("application/json");
				out.write(new Gson().toJson(w));
				out.println();
			} catch(IllegalArgumentException e) {
				response.sendError(500, e.toString());
			}catch (ExerciseException e) {
				// TODO Auto-generated catch block
				response.sendError(500, e.toString());
			}finally {
				th.close();
			}
			
			
		} else if (type.equals("updateWorkout")) {
			
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String[] exerciseList = request.getParameterValues("exerciseList[]");
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				
				Workout w = th.updateWorkout(id, name, exerciseList);
				
				response.setContentType("application/json");
				out.write(new Gson().toJson(w));
				out.println();
			} catch(IllegalArgumentException e) {
				response.sendError(500, e.toString());
			}catch (ExerciseException e) {
				// TODO Auto-generated catch block
				response.sendError(500, e.toString());
			}finally {
				th.close();
			}

		} else if(type.equals("assignWorkout") || type.equals("unassignWorkout")) {
			
			int customerId = Integer.parseInt(request.getParameter("customerId"));
			int workoutId = Integer.parseInt(request.getParameter("workoutId"));

			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				if(type.equals("assignWorkout")) {
					th.assignWorkout(customerId, workoutId);
				} else if(type.equals("unassignWorkout")){
					th.unassignWorkout(customerId, workoutId);
				}
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(IllegalArgumentException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));

			
		} else if (type.equals("deleteWorkout")) {
			TrainerHandler th= new TrainerHandler();
			try {
				th.deleteWorkout(request.getParameter("workoutId"));
				out.write("success");
			} catch(IllegalArgumentException e) {
				response.sendError(500, e.toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}

		}
	}
}