package com.jeegroupproject.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.*;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//test la présence du cookie d'auth
		//si présent, aller directement à la main page
		
		HttpServletRequest request = (HttpServletRequest) req; // Syntaxe pour caster
		HttpServletResponse response = (HttpServletResponse) res;
		Cookie[] cookies = request.getCookies(); // récupérer tous les cookies du domaine
		
		boolean needsLoginForm = true;
		if(cookies != null ){
			for (Cookie cookie : cookies) {
			   if (cookie.getName().equals("loggedIn")) {
				   if(cookie.getValue().equals("1")){
					    needsLoginForm = false;
						response.sendRedirect(request.getContextPath() + MAIN_PAGE);
				   }
			    }
			}
		}

		if(needsLoginForm){
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
		
		
		//first of all, test if there is a token and userid in cookies.
		Integer userId = null;
		String token = null;
		if(cookies != null ){
            for (Cookie cookie : cookies) {
                //  cookie authentication takes both userID and token in account for validation
                   if (cookie.getName().equals("userId")) { // get the value of userId cookie
                   userId = Integer.parseInt(cookie.getValue());         
                }
                if (cookie.getName().equals("token")) { // get the value of token cookie
                   token = cookie.getValue();         
                }
            }
        }

		if(userId != null && token != null){ //missing the cookies, one needs to login.
           
            //Try to find a user that matches that 
            User authenticatedUser = User.getAuthenticatedUserByToken(userId, token);
            if(authenticatedUser != null){ 
            	
    			response.sendRedirect(request.getContextPath() + "/restricted/main");// redirect to main
    			
            	
            }
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
			System.err.println("hash for entered password" + User.hashPassword(password));
			
			//remember : getAuthenticatedUser should generate auth_token for said user
			User connectedUser = User.getAuthenticatedUser( clientId.trim().isEmpty() ? null :  Integer.parseInt(clientId), email.trim().isEmpty() ? null: email, password);
			if(connectedUser != null){
				// place the token for user in cookie
				response.addCookie(new Cookie("token",connectedUser.getAccess_token())); //set cookie
				//place the user id in Cookie
				response.addCookie(new Cookie("userId", ((Integer)connectedUser.getId()).toString())); //set cookie
				response.sendRedirect(request.getContextPath() + "/restricted/main");// redirect to main
			}else{
				String messageNoAuth = "Utilisateur Inconnu ou mot de passe incorrect";
				request.setAttribute("message", messageNoAuth);
				this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);// show an error on login page
			}
			
			
			
			
		}
		
		
		
		
		
		
	}

}
