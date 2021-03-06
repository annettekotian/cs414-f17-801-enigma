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
import edu.colostate.cs.cs414.enigma.handler.builder.CustomerBuilder;
import edu.colostate.cs.cs414.enigma.handler.builder.GymSystemUserBuilder;
import edu.colostate.cs.cs414.enigma.handler.builder.ManagerBuilder;
import edu.colostate.cs.cs414.enigma.handler.builder.PersonalInformationBuilder;
import edu.colostate.cs.cs414.enigma.handler.builder.TrainerBuilder;

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
					machines = sysHandler.searchInventory(searchText);
				}
				values.put("machines", machines);
				out.write(new Gson().toJson(values));
			} catch(Exception e) {
				response.sendError(500, e.toString());
			} finally {
				sysHandler.close();
			}
		}
		break;
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
			createUpdateMachine(request, response, "create", values);
			break;
		}
			
		case "updateMachine": {
			createUpdateMachine(request, response, "update", values);
			break;
		}	
		
		case "deleteMachine": {
			ManagerHandler mh = new ManagerHandler();
			String uploadPath = getServletContext().getInitParameter("path_to_upload");
			try {
				mh.deleteMachine(request.getParameter("id"), uploadPath);
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

			ManagerBuilder mb = new ManagerBuilder();
			buildPersonalInformation(mb, request);
			buildGymSystemUser(mb, request);
			try {
				Manager m = mb.createManager();
				values.put("manager", m);
				values.put("status", "success");
				out.write(new Gson().toJson(values));
			} catch (IllegalArgumentException e) {
				values.put("status", "failure");
				values.put("message", e.getMessage());
				out.write(new Gson().toJson(values));
			} catch (PersistenceException e) {
				values.put("status", "failure");
				values.put("message", e.getMessage());
				out.write(new Gson().toJson(values));
			} catch (AddressException e) {
				values.put("status", "failure");
				values.put("message", e.getMessage());
				out.write(new Gson().toJson(values));
			} catch (Exception e) {
				response.sendError(500, e.toString());
			} finally {
				mb.close();
			}

			break;
		}
			
		case "createCustomer": {
			
			createUpdateCustomer(request, response, "create", values);		
			break;
		}
		
		case "updateCustomer":  {
			
			createUpdateCustomer(request, response, "update", values);
			break;
		}
		
		case "deleteCustomer": {
			String cId = request.getParameter("id");
			CustomerHandler ch = new CustomerHandler();
			try {
				ch.deleteCustomer(cId);
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
			
			createUpdateTrainer(request, response, "create", values);
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			break;
		}
		
		case "updateTrainer": {
			createUpdateTrainer(request, response, "update", values);	
			response.setContentType("application/json");
			out.write(new Gson().toJson(values));
			break;
		}
		
		case "deleteTrainer": {
			Map<String, Object> returnValues = new HashMap<String, Object>();
			TrainerHandler th = new TrainerHandler();
			try {
				th.deleteTrainer(Integer.parseInt(request.getParameter("id")));
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
	
	private void buildPersonalInformation (PersonalInformationBuilder pb, HttpServletRequest request) {
		pb.setFirstName(request.getParameter("firstName")).setLastName(request.getParameter("lastName")).setPhoneNumber(request.getParameter("phone"))
		.setEmail(request.getParameter("email")).setStreet(request.getParameter("street")).setCity(request.getParameter("city"))
		.setState(request.getParameter("state")).setZipcode(request.getParameter("zip")).setHealthInsurance(request.getParameter("healthInsurance"));
	
	}
	
	private void buildGymSystemUser(GymSystemUserBuilder gb, HttpServletRequest request) {
		gb.setUsername(request.getParameter("userName")).setPassword(request.getParameter("password")).setConfirmPassword(request.getParameter("confirmPassword"));
	}
	
	private TrainerBuilder getTrainerBuilderFromRequest(HttpServletRequest request) {
		TrainerBuilder tb = new TrainerBuilder();
		buildPersonalInformation(tb, request);
		buildGymSystemUser(tb, request);
		return tb;
	}
	
	private void createUpdateTrainer(HttpServletRequest request, HttpServletResponse response, String type, Map<String, Object> returnValues) throws IOException {
		TrainerBuilder tb = getTrainerBuilderFromRequest(request);
		try {
			if(type.equals("create"))
			{
				tb.createTrainer();
			} else {
				tb.updateTrainer(Integer.parseInt(request.getParameter("id")));
			}
			
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
			tb.close();
		}
		
	}
	
	private void createUpdateCustomer(HttpServletRequest request, HttpServletResponse response, String type, Map<String, Object> returnValues) throws IOException {
		CustomerBuilder cb = new CustomerBuilder();
		buildPersonalInformation(cb, request);
		buildGymSystemUser(cb, request);
		cb.setMembershipStatus(request.getParameter("membershipStatus"));
		
		try {
			Customer c = null;
			if(type == "create") {
				c = cb.createCustomer();	
			} else {
				c = cb.updateCustomer(Integer.parseInt(request.getParameter("id")));
			}
						
			returnValues.put("customer", c);
			returnValues.put("status", "success");
			response.getWriter().write(new Gson().toJson(returnValues));
					
		} catch (IllegalArgumentException e) {
			returnValues.put("status", "failure");
			returnValues.put("message",e.getMessage());
			response.getWriter().write(new Gson().toJson(returnValues));
		} catch(PersistenceException e) {
			returnValues.put("status", "failure");
			returnValues.put("message", e.getCause().getCause().toString());
			response.getWriter().write(new Gson().toJson(returnValues));
		} catch (AddressException e) {
			returnValues.put("status", "failure");
			returnValues.put("message",e.getMessage());
			response.getWriter().write(new Gson().toJson(returnValues));
		} catch (Exception e) {
			response.sendError(500, e.toString());
		} finally {
			cb.close();
		}
	}
	
	private void createUpdateMachine(HttpServletRequest request, HttpServletResponse response, String type, Map<String, Object> returnValues) throws IOException, ServletException {
		Part part = request.getPart("machinePic");
		InputStream content = part.getInputStream();
		String uploadPath = getServletContext().getInitParameter("path_to_upload");
		String quantity = request.getParameter("machineQuantity");
		String machineName = request.getParameter("machineName");
		ManagerHandler mh = new ManagerHandler();
		Machine m = null;
		try {
			if(type.equals("create")) {
				m = mh.createMachine(machineName, content, uploadPath, quantity);
			} else {
				m = mh.updateMachine(request.getParameter("machineId") , machineName, content, uploadPath, quantity);
			}
			
			returnValues.put("machine", m);
			response.getWriter().println(new Gson().toJson(returnValues));
		} catch (MachineException e) {
			response.sendError(500, e.toString());
		} catch (IllegalArgumentException e) {
			response.sendError(500, e.toString());
		} catch (PersistenceException e) {
			response.sendError(500, e.toString());
		} finally {
			mh.close();
		}
	}
	
}
