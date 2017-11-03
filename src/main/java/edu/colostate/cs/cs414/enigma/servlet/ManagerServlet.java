package edu.colostate.cs.cs414.enigma.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.MachineException;
import edu.colostate.cs.cs414.enigma.entity.Manager;

import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.WorkHoursException;
import edu.colostate.cs.cs414.enigma.handler.SystemHandler;
import edu.colostate.cs.cs414.enigma.handler.CustomerHandler;

import edu.colostate.cs.cs414.enigma.handler.ManagerHandler;

import edu.colostate.cs.cs414.enigma.handler.TrainerHandler;

/**
 * Servlet implementation class Manager
 */
@WebServlet({ "/manager/*" })
@MultipartConfig
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
		Map<String, Object> values = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();
		
		
		if(type.equals("searchTrainers")) {
			String value = request.getParameter("value");
			try {
				List<Trainer> trainers = new TrainerHandler().searchTrainers(value);
				response.setContentType("application/json");
				out.write(new Gson().toJson(trainers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		}
		else if(type.equals("getAllTrainers")) {
			try {
				TrainerHandler th = new TrainerHandler();
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getAllTrainers()));
				th.close();
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		}
		else if(type.equals("getTrainerById")) {
			Integer trainerId = Integer.parseInt(request.getParameter("trainerId"));
			try {
				TrainerHandler th = new TrainerHandler();
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getTrainerById(trainerId)));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		}
		else if(type.equals("getAllQualifications")) {
			try {
				TrainerHandler th = new TrainerHandler();
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getAllQualifications()));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		}	
		
		switch(type) {		
		case "getHealthInsurances":
			try {
				SystemHandler sh = new SystemHandler();
				List<HealthInsurance> healthInsurances = sh.getHealthInsurances();
				Gson gson = new Gson();
				String healthInsurancesJson = gson.toJson(healthInsurances);
				values.put("healthInsurances", healthInsurancesJson);
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			return;
			
		case "getStates":
			try {	
				SystemHandler sh = new SystemHandler();
				List<State> states = sh.getAllStates();
				Gson gson = new Gson();
				String statesJson = gson.toJson(states);
				values.put("states", statesJson);
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			return;
		
		case "getAllManagers" :
			
			try {
				List<Manager> managers = new ManagerHandler().getAllManagers();
				out.write(new Gson().toJson(managers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			
		return;
		
		case "getCustomerModalData":
			SystemHandler sh = new SystemHandler();
			values.put("healthInsurances", new Gson().toJson(sh.getHealthInsurances()));
			values.put("states", new Gson().toJson(sh.getAllStates()));
			
			SystemHandler sh1 = new SystemHandler();
			values.put("membershipType", new Gson().toJson((sh1.getMembershipTypes())));
			
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			
			return; 
			
		case "getAllCustomers": 
			try {
				List<Customer> customers = new CustomerHandler().getCustomers();
				out.write(new Gson().toJson(customers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
			
		case "getCustomerById": 
			try {
				Customer c  = new CustomerHandler().getCustomerById(Integer.parseInt(request.getParameter("id")));
				values.put("status", "success");
				values.put("customer", c);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return;
		case "getSearchCustomerResults" :
			
			try {
				String searchText = request.getParameter("searchText");
				CustomerHandler ch = new CustomerHandler();
				List<Customer> customers = new ArrayList<Customer>();
				if(searchText.isEmpty() ) {
					customers = ch.getCustomers();
				} else  {
					customers = ch.getCustomerByKeyword(searchText);
				}
				values.put("results", customers);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			}
			return; 
			
		case "getEditManagerData":
			
			
			try {
				Manager m = new ManagerHandler().getMangerById(request.getParameter("id"));
				
				values.put("manager", m);
				
				SystemHandler h = new SystemHandler();
				values.put("healthInsurances", h.getHealthInsurances());
				values.put("states", h.getAllStates());
				
				out.write(new Gson().toJson(values));
			
			}catch(Exception ex) {
				response.sendError(500, ex.toString());
				
			}
			
			return;
			
		case "getSearchManagerResults":
			try {
				String searchText = request.getParameter("searchText");
				List<Manager> managers = new ArrayList<Manager>();
				ManagerHandler mh1 = new ManagerHandler();
				if(searchText.isEmpty()) {
					managers = mh1.getAllManagers();
				} else {
					managers = mh1.searchManager(request.getParameter("searchText"));
				}
				
				values.put("results", managers);
				out.write(new Gson().toJson(values));
			}catch (Exception ex) {
				response.sendError(500, ex.toString());
			}
			return;
			
		case "getInventory": 
			try {
				List<Machine> machines = new SystemHandler().getInventory();
				values.put("machines", machines);
				out.write(new Gson().toJson(values));
			}catch (Exception e) {
				
			}
			break;
		default:
			response.sendError(404);
			break;
		
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		String type = request.getParameter("type");
		Map<String, Object> values = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();
		switch (type) {
		case "addMachine": 
			Part part = request.getPart("machinePic");
			InputStream content = part.getInputStream();
			String uploadPath = getServletContext().getInitParameter("path_to_upload");
			try {
				Machine m = new ManagerHandler().addMachine(request.getParameter("machineName"), content, uploadPath, request.getParameter("machineQuantity"));
				values.put("machine", m);
				out.println(new Gson().toJson(values));
			} catch ( MachineException e) {
				response.sendError(500, e.toString());
			} catch(IllegalArgumentException e) {
				response.sendError(500, e.toString());
			}
			
			break;

		case "createManager":

			String fName = request.getParameter("fName");
			String lName = request.getParameter("lName");
			String uName = request.getParameter("uName");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String street = request.getParameter("street");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zip = request.getParameter("zip");
			String insurance = request.getParameter("insurance");

			ManagerHandler mh = new ManagerHandler();
			try {
				Manager m = mh.createManager(email, fName, lName, phone, insurance, uName, password, confirmPassword, street, city, zip,
						state);
				values.put("manager", m);
				values.put("status", "success");
				out.write(new Gson().toJson(values));
			} catch (IllegalArgumentException e) {
				response.sendError(500, e.toString());
			} catch (PersistenceException e) {
				response.sendError(500, e.toString());
			}catch (AddressException e) {
				response.sendError(500, e.toString());
			}
			catch (Exception e) {
				response.sendError(500, e.toString());
			}

			break;
			
		case "createCustomer":
			String firstName = request.getParameter("fName");
			String lastName = request.getParameter("lName");
			String phoneNumber = request.getParameter("phone");
			email = request.getParameter("email");
			String streetCustomer = request.getParameter("street");
			String cityCustomer = request.getParameter("city");
			state = request.getParameter("state");
			String zipcode = request.getParameter("zip");
			String healthInsurance = request.getParameter("healthInsurance");
			String membershipStatus = request.getParameter("membershipStatus");
			
			try {
				Customer c = new CustomerHandler().createNewCustomer(email, firstName, lastName, phoneNumber, healthInsurance, streetCustomer, 
						cityCustomer, zipcode, state, membershipStatus);
				
				values.put("customer", c);
				values.put("status", "success");
				if(c == null) {
					values.put("status", "failure");
				}
				out.write(new Gson().toJson(values));
			}catch (IllegalArgumentException e) {
				response.sendError(500, e.toString());
			}
			catch(PersistenceException e) {
				response.sendError(500, e.toString());
			} catch (AddressException e) {
				response.sendError(500, e.toString());
			} catch (Exception e) {
				response.sendError(500, e.toString());
			}
			
			break;
		case "updateCustomer": 
			firstName = request.getParameter("fName");
			lastName = request.getParameter("lName");
			phoneNumber = request.getParameter("phone");
			email = request.getParameter("email");
			streetCustomer = request.getParameter("street");
			cityCustomer = request.getParameter("city");
			state = request.getParameter("state");
			zipcode = request.getParameter("zip");
			healthInsurance = request.getParameter("healthInsurance");
			membershipStatus = request.getParameter("membershipStatus");
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				Customer c = new CustomerHandler().updateCustomer(id, email, firstName, lastName, phoneNumber, healthInsurance, streetCustomer, 
						cityCustomer, zipcode, state, membershipStatus);
				
				values.put("customer", c);
				values.put("status", "success");
				if(c == null) {
					values.put("status", "failure");
				}
				out.write(new Gson().toJson(values));
			}catch(AddressException e) {
				response.sendError(500, e.toString());
			}catch(Exception e) {
				response.sendError(500, e.toString());
			}
			
			break;
		case "deleteCustomer":
			String cId = request.getParameter("id");
			new CustomerHandler().removeCustomer(cId);
			values.put("status", "success");
			out.write(new Gson().toJson(values));
		}
		
		
		if(type.equals("createTrainer")) {
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phoneNumber = request.getParameter("phone");
			String email = request.getParameter("email");
			String street = request.getParameter("street");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zipcode = request.getParameter("zip");
			String healthInsurance = request.getParameter("healthInsurance");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			try {
				Trainer newTrainer = new TrainerHandler().createNewTrainer(firstName, lastName, phoneNumber, email,
						street, city, state, zipcode, healthInsurance, userName, password, confirmPassword);	
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(AddressException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e);
			}
			catch(IllegalArgumentException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e);
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			}
			
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
		else if(type.equals("updateTrainer")) {
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phoneNumber = request.getParameter("phone");
			String email = request.getParameter("email");
			String street = request.getParameter("street");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zipcode = request.getParameter("zip");
			String healthInsurance = request.getParameter("healthInsurance");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			int trainerId = Integer.parseInt(request.getParameter("id"));
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			try {
				Trainer newTrainer = new TrainerHandler().modifyTrainer(trainerId, firstName, lastName, phoneNumber,
						email, street, city, state, zipcode, healthInsurance, userName, password, confirmPassword);	
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(AddressException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			}
			catch(IllegalArgumentException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			}
			
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
		else if(type.equals("deleteTrainer")) {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			try {
				new TrainerHandler().deleteTrainer(trainerId);
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
			}	
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
		else if(type.equals("addQualification")) {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			String qualification = request.getParameter("qualification");
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.addQualification(trainerId, qualification);
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
		else if(type.equals("deleteQualification")) {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			String qualification = request.getParameter("qualification");
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteQualification(trainerId, qualification);
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
		else if(type.equals("addWorkHours")) {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			int startYear = Integer.parseInt(request.getParameter("startYear"));
			int startMonth = Integer.parseInt(request.getParameter("startMonth"));
			int startDay = Integer.parseInt(request.getParameter("startDay"));
			int startHour = Integer.parseInt(request.getParameter("startHour"));
			int startMinute = Integer.parseInt(request.getParameter("startMinute"));
			int endYear = Integer.parseInt(request.getParameter("endYear"));
			int endMonth = Integer.parseInt(request.getParameter("endMonth"));
			int endDay = Integer.parseInt(request.getParameter("endDay"));
			int endHour = Integer.parseInt(request.getParameter("endHour"));
			int endMinute = Integer.parseInt(request.getParameter("endMinute"));

			Date startDatetime = new Date(startYear-1900, startMonth, startDay, startHour, startMinute);
			Date endDatetime = new Date(endYear-1900, endMonth, endDay, endHour, endMinute);
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.addWorkHours(trainerId, startDatetime, endDatetime);
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(WorkHoursException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
		else if(type.equals("deleteWorkHours")) {
			int trainerId = Integer.parseInt(request.getParameter("trainerId"));
			int workHoursId = Integer.parseInt(request.getParameter("workHoursId"));
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteWorkHours(trainerId, workHoursId);
				returnValues.put("rc", "0");
			}
			catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			}
			catch(Exception e) {
				response.sendError(500, e.toString());
			}
			finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
		}
	}
}
