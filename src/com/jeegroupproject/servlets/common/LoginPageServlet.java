package com.jeegroupproject.servlets.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.*;
import com.jeegroupproject.filters.IsAuthenticated;

/**
 * Servlet implementation class LoginPageServlet
 */
@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/common/loginPage.jsp";
	public static final String MAIN_AUTH_PAGE = "/authenticated/main";
	public static final String MAIN_ADVISOR_PAGE = "/advisor/main";
       
    public LoginPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		Cookie[] cookies = request.getCookies(); // get all cookies from the domain
		
		Person authenticatedPerson = IsAuthenticated.getAuthenticatedPersonFromCookies(cookies);
		
		if(authenticatedPerson != null){ // if user is already authenticated, no need to show login page
			if(authenticatedPerson.isAdvisor()){
				response.sendRedirect(request.getContextPath() + MAIN_ADVISOR_PAGE); 
			}else{
				response.sendRedirect(request.getContextPath() + MAIN_AUTH_PAGE);
			}
		}else{
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		}	
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String externalId = request.getParameter("externalId");
		String password = request.getParameter("password");
		
		Cookie[] cookies = request.getCookies(); // get all cookies for the current domain (IE, those that are sent by client)
		
		Person authenticatedPerson = IsAuthenticated.getAuthenticatedPersonFromCookies(cookies);
		if(authenticatedPerson != null){
			if(authenticatedPerson.isAdvisor()){
				response.sendRedirect(request.getContextPath() + MAIN_ADVISOR_PAGE);
			}else{
				response.sendRedirect(request.getContextPath() + MAIN_AUTH_PAGE);
			}
		}
		
		
		//Testing submitted values by the user.
		if(email.trim().isEmpty() && externalId.trim().isEmpty()){ // Fail if both client id and email are empty
			
			String messageId = "Vous n'avez indiqué ni votre identifiant ni votre e-mail";
			request.setAttribute("message", messageId);
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
			
		}else if(password.trim().isEmpty()){ // Fail if password is empty
			
			String messagePassword = "Vous n'avez pas rentré votre mot de passe";
			request.setAttribute("message", messagePassword);
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
			
		}else{
			
			//TODO : remove this next line when a person password change is implemented.
			//It is only here to help grabbing hashed passwords to then insert them into the database.
			System.err.println("hash for entered password" + Person.hashPassword(password));
			
			//remember : getAuthenticatedPerson should generate a token for said person
			// trim method is used to remove unwanted space char (for example if a user enters a space by mistake just after or before its email)
			Person connectedPerson = Person.getAuthenticatedPerson( externalId.trim().isEmpty() ? null :  Integer.parseInt(externalId), email.trim().isEmpty() ? null: email, password);
			
			if(connectedPerson != null){
				
				// place the token for user in cookie
				response.addCookie(new Cookie(IsAuthenticated.TOKEN_COOKIE_NAME,connectedPerson.getToken())); //set cookie
				//place the user id in Cookie
				response.addCookie(new Cookie(IsAuthenticated.PERSONID_COOKIE_NAME, ((Integer)connectedPerson.getId()).toString())); //set cookie
				if(connectedPerson.isAdvisor()){
					response.sendRedirect(request.getContextPath() + MAIN_ADVISOR_PAGE);
				}else{
					response.sendRedirect(request.getContextPath() + MAIN_AUTH_PAGE);
				}
			}else{
				
				String messageNoAuth = "Utilisateur Inconnu ou mot de passe incorrect";
				request.setAttribute("message", messageNoAuth);
				this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
				
			}		
			
		}	
		
	}

}
