package com.jeegroupproject.servlets.advisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Person;

/**
 * Servlet implementation class ClientInformationServlet
 */
@WebServlet("/ClientInformationServlet")
public class ClientInformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/advisor/clientinformation.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientInformationServlet() {
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
