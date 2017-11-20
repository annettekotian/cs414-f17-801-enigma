package edu.colostate.cs.cs414.enigma.handler;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.PersistenceException;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.exception.MachineException;

public class ManagerHandler extends GymSystemHandler {	
	
	/**
	 * 
	 * @param email: String
	 * @param firstName: String:
	 * @param lastName: String
	 * @param phoneNumber: String
	 * @param hiId: String health insruance id
	 * @param userName: String username of the manager using which he will log in
	 * @param userPass: String password for the username
	 * @param street: String password for the username
	 * @param city: String city
	 * @param zip: String zipcode
	 * @param state: String state
	 * @return
	 */
	public Manager createManager(String email, String firstName, String lastName, String phoneNumber, String insurance, String userName, String userPass,
			String confirmPassword, String street, String city, String zip, String state) throws AddressException  {
		

		// password validations	
		if(!userPass.equals(confirmPassword) || userPass.length() < 8) {
			throw new IllegalArgumentException("Password error");
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", insurance);
		HealthInsurance hiDB = (HealthInsurance) getDao().querySingle("HealthInsurance.findByName", parameters);
		if(hiDB == null) {
			hiDB = new HealthInsurance(insurance);
		}
		
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateDB = (State) getDao().querySingle("State.findState", stateParams);
		Address address = new Address(street, city, zip, stateDB);
		
		PersonalInformation p = new PersonalInformation(email, firstName, lastName, phoneNumber, hiDB, address);
		Map<String, Object> userLevelParams = new HashMap<String, Object>(); 
		userLevelParams.put("level", "MANAGER");
		UserLevel ul = (UserLevel) getDao().querySingle("UserLevel.findLevel", userLevelParams);
		User user = new User( userName, userPass, ul);
		Manager manager= new Manager(p, user);
		
		// Persist the manager with the database
		getDao().persist(manager);
		
		return manager;		
	}
	
	public List<Manager> getAllManagers() {
			
		// Issue a query to get all the customers
		List<Manager> managers = new ArrayList<Manager>();
		List<?> results = getDao().query("Manager.findAll", null);
		for(int i=0; i<results.size(); i++) {
			managers.add((Manager) results.get(i));
		}		
		return managers;
	}
	
	

	/**
	 * this method returns the manager object by its id
	 * @param id: String the db id of the Manager
	 * @return Manager
	 */
	public Manager getMangerById(String id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", Integer.parseInt(id));
		Manager m = (Manager) getDao().querySingle("Manager.findById", parameters);
		return m;
	}
	
	/**
	 * This method searches the customer table and returns a list of customers matching the keyword. It looks for matches in 
	 * the firstName, last name, email, phone, city, state, zip, street, health insurance and membership. 
	 * @param keywords
	 * @return
	 */
	
	public List<Manager> searchManager(String keywords) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("keyword", "%" + keywords + "%");
		List<?> results = getDao().query("Manager.findByKeywords", parameters);
		List<Manager> managers= new ArrayList<Manager>();
		for(int i=0; i<results.size(); i++) {
			managers.add((Manager) results.get(i));
		}
		return managers;
	}
	
	/**
	 * 
	 * @param name: String name of the image file to be saved
	 * @param fileContent: InputStream of the image file
	 * @param uploadPath: String path where the file is to be saved
	 * @param quantity: String: number of machines
	 * @return
	 * @throws IOException
	 * @throws MachineException
	 */
	public Machine addMachine(String name, InputStream fileContent, String uploadPath, String quantity) throws IOException, MachineException {
		
		// validations
		if(name.isEmpty() || fileContent == null || fileContent.available()==0 || uploadPath.isEmpty() || quantity.isEmpty()) {
			throw new IllegalArgumentException("missing input");
		}
		BufferedImage image = ImageIO.read(fileContent);
		if(image == null) {
			throw new IllegalArgumentException("not image file");
		}		
		
		String fullName = name + ".png";
		int noOfMachines = Integer.parseInt(quantity);
		Machine m = new Machine(name, fullName, noOfMachines);
		getDao().persist(m);
		int machineId = m.getId();
		String fullNameWithId = machineId + "_" + fullName;
		m.setPictureLocation(fullNameWithId);
		getDao().update(m);
		String path = uploadPath +   "/"+ fullNameWithId;
		File imageFile = new File(path);
		ImageIO.write(image, "png", imageFile);
		return m;
		
	}
	
	public Machine getMachineById(String id) {	
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		return (Machine)getDao().querySingle("Machine.findId", params);
	}
	
	
	/**this methods updates a machine. If the name has been changes, it updates the name in the db and the name of the image file stored in the 
	 * disk. If the image has been changed it stores the new image in the disk.
	 * @param id: String db id of the  machine being updated.
	 * @param name: String name of the image file to be saved
	 * @param fileContent: InputStream of the image file
	 * @param uploadPath: String path where the file is to be saved
	 * @param quantity: String: number of machines
	 * @return
	 * @throws IOException
	 * @throws MachineException
	 */
	public Machine updateMachine(String id, String name, InputStream fileContent, String uploadPath, String quantity) throws IOException, MachineException {
		
		// validations
		if(id.isEmpty() || name.isEmpty() ||  uploadPath.isEmpty() || quantity.isEmpty()) {
			throw new IllegalArgumentException("missing input");
		}
		BufferedImage image = null;
		boolean picChanged = false;
		if(fileContent != null && fileContent.available() > 0 ) {
			image = ImageIO.read(fileContent);
			picChanged = true;
			if(image == null) {
				throw new IllegalArgumentException("not image file");
			}
		}		
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		Machine m = (Machine) getDao().querySingle("Machine.findId", params);
		
		// get the current machine name
		String currentName = m.getName();
		
		// set the new full name
		String fullName = name + ".png";
		int noOfMachines = Integer.parseInt(quantity);
		//set full name with db id
		String fullNameWithId = m.getId() + "_" + fullName;
		
		m.setName(name);
		m.setPictureLocation(fullNameWithId);
		m.setQuantity(Integer.parseInt(quantity));
		
		getDao().update(m);

		String currentPath = uploadPath +   "/"+ m.getId() + "_" + currentName + ".png";
		//System.out.println(currentPath);
		File currentFile = new File(currentPath);
		String path = uploadPath +   "/"+ fullNameWithId;
		//System.out.println(path);
		File imageFile = new File(path);
		
		// if name has been updated update the picture in the storage disk too
		if(!currentName.equals(name) && picChanged == false) {
			currentFile.renameTo(imageFile);
			// write the new image to the storage disk	

		}
		
		if (picChanged == true) {
			ImageIO.write(image, "png", imageFile);
		}
		return m;
		
	}

	
	public void removeMachine(String id, String uploadPath) {
		int mId = Integer.parseInt(id);
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", mId);
		Machine m = (Machine) getDao().querySingle("Machine.findId", params);
		if(m == null) {
			return;
		}
		String location = m.getPictureLocation();
		getDao().remove(m);
		File f = new File(uploadPath + "/" + location);
		f.delete();
				
	}
}
