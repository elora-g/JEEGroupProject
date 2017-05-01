package com.jeegroupproject.servlets.advisor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Person;
import com.jeegroupproject.filters.IsAuthenticated;

/**
 * Servlet implementation class ClientsServlet
 */
@WebServlet("/ClientsServlet")
public class ClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/advisor/clients.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		Boolean clientType = request.getParameter("clientType").equals("1") ? true : false;
		// getAttribute returns an object, we have to cast it to Person to get its id. 
		//As the authenticated user is the advisor, we will pass its id as the setAdvisorId for the new client
		int advisorId = ((Person)request.getAttribute(IsAuthenticated.AUTH_PERSON_ATTR_NAME)).getId();
		int externalId = Person.getUniqueExternalId();
		System.err.println(firstName);
		
		//instanciate new person
		Person newPerson = new Person();
		newPerson.setId(-1); // show to persist that's it is a new person
		newPerson.setLastname(lastName);
		newPerson.setFirstname(firstName);
		newPerson.setDob(dob);
		newPerson.setEmail(email);
		newPerson.setPassword(password);
		newPerson.setPhoneNumber(phoneNumber);
		newPerson.setIsAdvisor(clientType);
		newPerson.setAdvisorId(advisorId);
		newPerson.setExternalId(externalId);
		
		// apply persist
		newPerson.persist();
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);//refresh
		
		
	}

}
