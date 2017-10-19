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

import edu.colostate.cs.cs414.enigma.handler.HealthInsuranceHandler;

/**
 * Servlet implementation class Manager
 */
@WebServlet({ "/Manager/ui", "/manager/ui" })
public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String type = request.getParameter("type");
		Map<String, String> values = new HashMap<String, String>();
		switch (type) {
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
		}
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(values));
	}

}
