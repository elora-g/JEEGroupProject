package com.jeegroupproject.servlets.advisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Account;
import com.jeegroupproject.beans.Person;

/**
 * Servlet implementation class clientServlet
 */
@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/advisor/client.jsp";
	public static final String CURRENT_CLIENT_ATTR_NAME = "currentClient";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Person currentClient = Person.getPersonByExternalId(Integer.parseInt(request.getParameter("id")));
		request.setAttribute(CURRENT_CLIENT_ATTR_NAME, currentClient);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get request parameters
		Person currentClient = Person.getPersonByExternalId(Integer.parseInt(request.getParameter("id")));
		request.setAttribute(CURRENT_CLIENT_ATTR_NAME, currentClient);
		String type = request.getParameter("accountType");
		String balance = request.getParameter("balance");
		int nbAccountsofcurrentClient = currentClient.getAccounts().size();
		
		boolean isDefault = true; // if the radio button is not displayed then it's the first account of the client and we set it as its default account
		boolean canContinue = true;
		
		if(nbAccountsofcurrentClient >0){
			if(request.getParameter("isDefault")!=null){ // if the radio button is displayed we get its value)
				isDefault = request.getParameter("isDefault").equals("1") ? true : false;
			}else{
				String message = "Vous n'avez pas indiqué si il s'agit du compte par défaut";
				request.setAttribute("message", message);
				canContinue = false;
			}
		}
		
		if(type == null){
			String message = "Vous n'avez pas indiqué de type de compte";
			request.setAttribute("message", message);
			canContinue = false;
		}
		
		if(balance.trim().isEmpty()){
			String message = "Vous n'avez pas indiqué de type de solde initial pour le compte";
			request.setAttribute("message", message);
			canContinue = false;
		}
		
		if(canContinue){
			
			//instanciate new account
			Account newAccount = new Account();
			newAccount.setId(-1);
			newAccount.setType(type);
			newAccount.setBalance(Float.parseFloat(balance));
			newAccount.setIsDefault(isDefault);
			newAccount.setCustomerId(((Integer)currentClient.getId()).toString());
			
			// apply persist
			newAccount.persist();
			
		}
		
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);//refresh
	}

}
