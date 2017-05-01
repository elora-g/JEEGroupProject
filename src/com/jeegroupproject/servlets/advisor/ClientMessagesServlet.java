package com.jeegroupproject.servlets.advisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Message;
import com.jeegroupproject.beans.Person;


/**
 * Servlet implementation class ClientMessagesServlet
 */
@WebServlet("/ClientMessagesServlet")
public class ClientMessagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/advisor/clientmessages.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientMessagesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Person currentClient = Person.getPersonByExternalId(Integer.parseInt(request.getParameter("id")));
		request.setAttribute(ClientServlet.CURRENT_CLIENT_ATTR_NAME, currentClient);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messageToAdvisor = request.getParameter("messageToClient");
		
		Person currentClient = Person.getPersonByExternalId(Integer.parseInt(request.getParameter("id")));
		request.setAttribute(ClientServlet.CURRENT_CLIENT_ATTR_NAME, currentClient);
		
		if(messageToAdvisor.trim().isEmpty() ){
			String message = "Vous n'avez rien indiqué";
			request.setAttribute("message", message);
		}else if(messageToAdvisor.length() > 128){ //this field in database is of type varchar 128
			String message = "Votre message est trop long (max 128 caractères)";
			request.setAttribute("message", message);
		}else{
			Message newMessage = new Message();
			newMessage.setContent(messageToAdvisor);
			newMessage.setTo(currentClient.getId());
			newMessage.setFrom(currentClient.getAdvisorId());
			newMessage.persist();
		}
		

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		
	}

}
