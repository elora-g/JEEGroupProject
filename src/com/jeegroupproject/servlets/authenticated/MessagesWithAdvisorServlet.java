package com.jeegroupproject.servlets.authenticated;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Message;
import com.jeegroupproject.beans.Person;
import com.jeegroupproject.filters.IsAuthenticated;

/**
 * Servlet implementation class MessagesWithAdvisorServlet
 */
@WebServlet("/MessagesWithAdvisorServlet")
public class MessagesWithAdvisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW = "/WEB-INF/Views/authenticated/messageswithadvisor.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagesWithAdvisorServlet() {
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

		String messageToAdvisor = request.getParameter("messageToAdvisor");
		
		Person authenticatedPerson = (Person) request.getAttribute(IsAuthenticated.AUTH_PERSON_ATTR_NAME);//cast because getAttribute only retuns objects
		
		if(messageToAdvisor.trim().isEmpty() ){
			String message = "Vous n'avez rien indiqué";
			request.setAttribute("message", message);
		}else if(messageToAdvisor.length() > 128){ //this field in database is of type varchar 128
			String message = "Votre message est trop long (max 128 caractères)";
			request.setAttribute("message", message);
		}else{
			Message newMessage = new Message();
			newMessage.setContent(messageToAdvisor);
			newMessage.setFrom(authenticatedPerson.getId());
			newMessage.setTo(authenticatedPerson.getAdvisorId());
			newMessage.persist();
		}
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

}
