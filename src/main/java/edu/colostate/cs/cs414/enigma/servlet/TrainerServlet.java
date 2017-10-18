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
				TrainerHandler trainerHandler = new TrainerHandler();
				values.put("customers", new Gson().toJson(trainerHandler.getCustomers()));
				values.put("rc", "0");
				trainerHandler.close();
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
