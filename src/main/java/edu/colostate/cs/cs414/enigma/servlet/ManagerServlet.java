package edu.colostate.cs.cs414.enigma.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.colostate.cs.cs414.enigma.entity.Manager;

import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.exception.MachineException;
import edu.colostate.cs.cs414.enigma.entity.exception.WorkHoursException;
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
		
		switch(type) {
		case "searchTrainers": {
			String value = request.getParameter("value");
			TrainerHandler th = new TrainerHandler();
			try {
				List<Trainer> trainers = th.searchTrainers(value);
				response.setContentType("application/json");
				out.write(new Gson().toJson(trainers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			return;
		}
		
		case "getAllTrainers": {
			TrainerHandler th = new TrainerHandler();
			try {
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getAllTrainers()));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			return;
		}
		
		case "getTrainerById": {
			Integer trainerId = Integer.parseInt(request.getParameter("trainerId"));
			TrainerHandler th = new TrainerHandler();
			try {
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getTrainerById(trainerId)));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			return;
		}
		
		case "getAllQualifications": {
			TrainerHandler th = new TrainerHandler();
			try {
				response.setContentType("application/json");
				out.write(new Gson().toJson(th.getAllQualifications()));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				th.close();
			}
			return;
		}
		
		case "getHealthInsurances": {
			SystemHandler sh = new SystemHandler();
			try {
				List<HealthInsurance> healthInsurances = sh.getHealthInsurances();
				values.put("healthInsurances", new Gson().toJson(healthInsurances));
				response.setContentType("application/json");
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sh.close();
			}
			return;
		}
		
		case "getStates": {
			SystemHandler sh = new SystemHandler();
			try {	
				List<State> states = sh.getAllStates();
				values.put("states", new Gson().toJson(states));
				response.setContentType("application/json");
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sh.close();
			}
			return;
		}
		
		case "getAllManagers": {
			ManagerHandler mh = new ManagerHandler();
			try {
				List<Manager> managers = mh.getAllManagers();
				out.write(new Gson().toJson(managers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				mh.close();
			}
			return;
		}
		
		case "getCustomerModalData": {
			SystemHandler sh = new SystemHandler();
			try {
				values.put("healthInsurances", new Gson().toJson(sh.getHealthInsurances()));
				values.put("states", new Gson().toJson(sh.getAllStates()));
				values.put("membershipType", new Gson().toJson((sh.getMembershipTypes())));
				response.setContentType("application/json");
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sh.close();
			}	
			return;
		}
		
		case "getAllCustomers": {
			CustomerHandler ch = new CustomerHandler();
			try {
				List<Customer> customers = ch.getCustomers();
				out.write(new Gson().toJson(customers));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			return;
		}
		
		case "getCustomerById": {
			int customerId = Integer.parseInt(request.getParameter("id"));
			CustomerHandler ch = new CustomerHandler();
			try {
				Customer c  = ch.getCustomerById(customerId);
				values.put("status", "success");
				values.put("customer", c);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			return;
		}
		
		case "getSearchCustomerResults": {
			String searchText = request.getParameter("searchText");
			CustomerHandler ch = new CustomerHandler();
			List<Customer> customers;
			try {
				if(searchText.isEmpty() ) {
					customers = ch.getCustomers();
				} else  {
					customers = ch.getCustomerByKeyword(searchText);
				}
				values.put("results", customers);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			return;
		}
		
		case "getEditManagerData": {
			String managerId = request.getParameter("id");
			ManagerHandler mh = new ManagerHandler();
			SystemHandler sh = new SystemHandler();
			try {
				Manager m = mh.getMangerById(managerId);
				values.put("manager", m);
				values.put("healthInsurances", sh.getHealthInsurances());
				values.put("states", sh.getAllStates());
				out.write(new Gson().toJson(values));
			} catch(Exception ex) {
				response.sendError(500, ex.toString());
			} finally {
				mh.close();
				sh.close();
			}
			return;
		}
		
		case "getSearchManagerResults": {
			String searchText = request.getParameter("searchText");
			ManagerHandler mh = new ManagerHandler();
			List<Manager> managers;
			try {
				if(searchText.isEmpty()) {
					managers = mh.getAllManagers();
				} else {
					managers = mh.searchManager(searchText);
				}
				values.put("results", managers);
				out.write(new Gson().toJson(values));
			} catch (Exception ex) {
				response.sendError(500, ex.toString());
			} finally {
				mh.close();
			}
			return;
		}
		
		case "getInventory": {
			SystemHandler sh = new SystemHandler();
			try {
				List<Machine> machines = sh.getInventory();
				values.put("machines", machines);
				out.write(new Gson().toJson(values));
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sh.close();
			}
			return;
		}
		
		case "getMachineById": {
			String machineId = request.getParameter("id");
			ManagerHandler mh = new ManagerHandler();
			try {
				Machine m = mh.getMachineById(machineId);
				values.put("machine", m);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				mh.close();
			}
			return;
		}
		
		case "getSearchMachineResults": {
			String searchText = request.getParameter("searchText");
			SystemHandler sysHandler = new SystemHandler();
			List<Machine> machines = null;
			try {				
				if(searchText.isEmpty()) {
					machines = sysHandler.getInventory();
				} else {
					machines = sysHandler.searchByKeyword(searchText);
				}
				values.put("machines", machines);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sysHandler.close();
			}
		}
		
		default: {
			response.sendError(404);
			break;
		}
		
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
		case "addMachine": {
			Part part = request.getPart("machinePic");
			InputStream content = part.getInputStream();
			String uploadPath = getServletContext().getInitParameter("path_to_upload");
			ManagerHandler mh = new ManagerHandler();
			try {
				Machine m = mh.addMachine(request.getParameter("machineName"), content, uploadPath,
						request.getParameter("machineQuantity"));
				values.put("machine", m);
				out.println(new Gson().toJson(values));
			} catch (MachineException e) {
				response.sendError(500, e.toString());
			} catch (IllegalArgumentException e) {
				response.sendError(500, e.toString());
			} catch (PersistenceException e) {
				response.sendError(500, e.toString());
			} finally {
				mh.close();
			}
			break;
		}
			
		case "updateMachine": {
			Part part = request.getPart("machinePic");
			InputStream content = part.getInputStream();
			String uploadPath = getServletContext().getInitParameter("path_to_upload");
			ManagerHandler mh = new ManagerHandler();
			try {
				Machine m = mh.updateMachine(request.getParameter("machineId") ,request.getParameter("machineName"), 
						content, uploadPath, request.getParameter("machineQuantity"));
				values.put("machine", m);
				out.println(new Gson().toJson(values));
			} catch (MachineException e) {
				response.sendError(500, e.toString());
			} catch (IllegalArgumentException e) {
				response.sendError(500, e.toString());
			} catch (PersistenceException e) {
				response.sendError(500, e.toString());
			} finally {
				mh.close();
			}
			break;
		}	
		
		case "deleteMachine": {
			ManagerHandler mh = new ManagerHandler();
			String uploadPath = getServletContext().getInitParameter("path_to_upload");
			try {
				mh.removeMachine(request.getParameter("id"), uploadPath);
				values.put("status", "success");
				out.write(new Gson().toJson(values));
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				mh.close();
			}
			break;
		}
			
		case "createManager": {

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
			} catch (AddressException e) {
				response.sendError(500, e.toString());
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				mh.close();
			}

			break;
		}
			
		case "createCustomer": {
			String firstName = request.getParameter("fName");
			String lastName = request.getParameter("lName");
			String phoneNumber = request.getParameter("phone");
			String email = request.getParameter("email");
			String streetCustomer = request.getParameter("street");
			String cityCustomer = request.getParameter("city");
			String state = request.getParameter("state");
			String zipcode = request.getParameter("zip");
			String healthInsurance = request.getParameter("healthInsurance");
			String membershipStatus = request.getParameter("membershipStatus");
			
			CustomerHandler ch = new CustomerHandler();
			try {
				Customer c = ch.createNewCustomer(email, firstName, lastName, phoneNumber, healthInsurance, streetCustomer, 
						cityCustomer, zipcode, state, membershipStatus);
				
				values.put("customer", c);
				values.put("status", "success");
				if(c == null) {
					values.put("status", "failure");
				}
				out.write(new Gson().toJson(values));
			} catch (IllegalArgumentException e) {
				response.sendError(500, e.toString());
			} catch(PersistenceException e) {
				response.sendError(500, e.toString());
			} catch (AddressException e) {
				response.sendError(500, e.toString());
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			
			break;
		}
		
		case "updateCustomer":  {
			String firstName = request.getParameter("fName");
			String lastName = request.getParameter("lName");
			String phoneNumber = request.getParameter("phone");
			String email = request.getParameter("email");
			String streetCustomer = request.getParameter("street");
			String cityCustomer = request.getParameter("city");
			String state = request.getParameter("state");
			String zipcode = request.getParameter("zip");
			String healthInsurance = request.getParameter("healthInsurance");
			String membershipStatus = request.getParameter("membershipStatus");
			int id = Integer.parseInt(request.getParameter("id"));
			
			CustomerHandler ch = new CustomerHandler();
			try {
				Customer c = ch.updateCustomer(id, email, firstName, lastName, phoneNumber, healthInsurance, streetCustomer, 
						cityCustomer, zipcode, state, membershipStatus);
				
				values.put("customer", c);
				values.put("status", "success");
				if(c == null) {
					values.put("status", "failure");
				}
				out.write(new Gson().toJson(values));
			} catch(AddressException e) {
				response.sendError(500, e.toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			
			break;
		}
		
		case "deleteCustomer": {
			String cId = request.getParameter("id");
			CustomerHandler ch = new CustomerHandler();
			try {
				ch.removeCustomer(cId);
				values.put("status", "success");
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				ch.close();
			}
			break;
		}
		
		case "createTrainer": {
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
			TrainerHandler th = new TrainerHandler();
			try {
				th.createNewTrainer(firstName, lastName, phoneNumber, email,street, city, state,
						zipcode, healthInsurance, userName, password, confirmPassword);	
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(AddressException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e);
			} catch(IllegalArgumentException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e);
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		case "updateTrainer": {
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
			TrainerHandler th = new TrainerHandler();
			try {
				th.modifyTrainer(trainerId, firstName, lastName, phoneNumber, email, street,
						city, state, zipcode, healthInsurance, userName, password, confirmPassword);	
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(AddressException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(IllegalArgumentException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		case "deleteTrainer": {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteTrainer(trainerId);
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		case "addQualification": {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			String qualification = request.getParameter("qualification");
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.addQualification(trainerId, qualification);
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		case "deleteQualification": {
			int trainerId = Integer.parseInt(request.getParameter("id"));
			String qualification = request.getParameter("qualification");
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteQualification(trainerId, qualification);
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		case "addWorkHours": {
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
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(WorkHoursException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getMessage());
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		case "deleteWorkHours": {
			int trainerId = Integer.parseInt(request.getParameter("trainerId"));
			int workHoursId = Integer.parseInt(request.getParameter("workHoursId"));
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteWorkHours(trainerId, workHoursId);
				returnValues.put("rc", "0");
			} catch(PersistenceException e) {
				returnValues.put("rc", "1");
				returnValues.put("msg", e.getCause().getCause().toString());
			} catch(Exception e) {
				response.sendError(500, e.toString());
				return;
			} finally {
				th.close();
			}
			response.setContentType("application/json");
			out.write(new Gson().toJson(returnValues));
			break;
		}
		
		default: {
			response.sendError(404);
			break;
		}
		
		}
	}
}
