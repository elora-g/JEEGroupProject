package com.jeegroupproject.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.*;
import com.jeegroupproject.filters.IsLoggedIn;

/**
 * Servlet implementation class LoginPageServlet
 */
@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/loginPage.jsp";
	public static final String MAIN_PAGE = "/restricted/main";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//test la présence du cookie d'auth
		//si présent, aller directement à la main page

		Cookie[] cookies = request.getCookies(); // récupérer tous les cookies du domaine
		
		Person authenticatedPerson = IsLoggedIn.getAuthenticatedPersonFromCookies(cookies);
		
		if(authenticatedPerson != null){
			response.sendRedirect(request.getContextPath() + MAIN_PAGE);// redirect to main
		}else{
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String clientId = request.getParameter("clientId");
		String password = request.getParameter("password");
		
		Cookie[] cookies = request.getCookies(); // get all cookies for the current domain (IE, those that are sent by client)
		
		Person authenticatedPerson = IsLoggedIn.getAuthenticatedPersonFromCookies(cookies);
		if(authenticatedPerson != null){
			response.sendRedirect(request.getContextPath() + MAIN_PAGE);// redirect to main
		}
		
		
		//Testing given values.
		if(email.trim().isEmpty() && clientId.trim().isEmpty()){ // Fail if both client id and email are empty
			String messageId = "Vous n'avez indiqué ni votre identifiant ni votre e-mail";
			request.setAttribute("message", messageId);
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
		}else if(password.trim().isEmpty()){ // Fail if password is empty
			String messagePassword = "Vous n'avez pas rentré votre mot de passe";
			request.setAttribute("message", messagePassword);
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
		}else{
			
			//TODO : remove this next line.
			//It is only here to help grabbing hashed passwords to then insert them into the database.
			System.err.println("hash for entered password" + Person.hashPassword(password));
			
			//remember : getAuthenticatedUser should generate auth_token for said user
			Person connectedUser = Person.getAuthenticatedPerson( clientId.trim().isEmpty() ? null :  Integer.parseInt(clientId), email.trim().isEmpty() ? null: email, password);
			if(connectedUser != null){
				// place the token for user in cookie
				response.addCookie(new Cookie("token",connectedUser.getToken())); //set cookie
				//place the user id in Cookie
				response.addCookie(new Cookie("userId", ((Integer)connectedUser.getId()).toString())); //set cookie
				response.sendRedirect(request.getContextPath() + MAIN_PAGE);// redirect to main
			}else{
				String messageNoAuth = "Utilisateur Inconnu ou mot de passe incorrect";
				request.setAttribute("message", messageNoAuth);
				this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
			}
			
			
			
			
		}
		
		
		
		
		
		
	}

}
