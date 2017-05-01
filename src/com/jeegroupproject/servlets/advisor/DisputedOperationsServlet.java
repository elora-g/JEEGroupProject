package com.jeegroupproject.servlets.advisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Account;
import com.jeegroupproject.beans.Operation;

/**
 * Servlet implementation class DisputedOperationsServlet
 */
@WebServlet("/DisputedOperationsServlet")
public class DisputedOperationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/advisor/disputedoperations.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisputedOperationsServlet() {
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
		Operation disputedOperation = Operation.getOperationById(Integer.parseInt(request.getParameter("operationid")));
		boolean cancelOperation = request.getParameter("disputeDecision").equals("1") ? true : false;
		if(cancelOperation){
			// delete the operation and give money back
			Account account = Account.getAccountById(disputedOperation.getAccountId());
			account.setBalance(account.getBalance() + disputedOperation.getAmount());
			account.persist();
			disputedOperation.delete();
		}else{
			disputedOperation.setDispute(false);
			disputedOperation.persist();
		}
		

		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);//refresh
	}

}
