package com.jeegroupproject.servlets.authenticated;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Operation;
import com.jeegroupproject.beans.Person;

/**
 * Servlet implementation class accountsServlet
 */
@WebServlet("/AccountsServlet")
public class AccountsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/authenticated/accounts.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountsServlet() {
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
	
		//get request parameters
		Operation newDisputedOperation = Operation.getOperationById(Integer.parseInt(request.getParameter("operationid")));
		newDisputedOperation.setDispute(true);
		newDisputedOperation.persist();

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		
				
	}

}
