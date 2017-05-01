package com.jeegroupproject.servlets.authenticated;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Account;
import com.jeegroupproject.beans.Person;
import com.jeegroupproject.filters.IsAuthenticated;

/**
 * Servlet implementation class PersonalInformationServlet
 */
@WebServlet("/PersonalInformationServlet")
public class PersonalInformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VIEW = "/WEB-INF/Views/authenticated/personalinformation.jsp";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonalInformationServlet() {
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
		
		Person authenticatedPerson = (Person) request.getAttribute(IsAuthenticated.AUTH_PERSON_ATTR_NAME);//cast because getAttribute only retuns objects
		
		//get request parameters
		String changeType = request.getParameter("changeType");
		if(changeType.equals("personalInfo")){
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			
			if(!email.trim().isEmpty()){
				authenticatedPerson.setEmail(email.trim());
			}
			if(!phoneNumber.trim().isEmpty()){
				authenticatedPerson.setPhoneNumber(phoneNumber.trim());
			}
			if(email.trim().isEmpty() && phoneNumber.trim().isEmpty()){
				String messagePersonalInfo = "Vous n'avez rien indiqué";
				request.setAttribute("messagePersonalInfo", messagePersonalInfo);
			}
			
			authenticatedPerson.persist();
			
		}else{
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String newPasswordConfirm = request.getParameter("newPasswordConfirm");
			String messagePassword;
			
			//We don't trim input password as the user might want to space at the end or beginning of his password
			if(oldPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()){
				messagePassword = "Vous n'avez pas rempli les trois champs";
				request.setAttribute("messagePassword", messagePassword);
			}else if(!newPassword.equals(newPasswordConfirm)){
				messagePassword = "Les mots de passe ne correspondent pas";
				request.setAttribute("messagePassword", messagePassword);
			}else{
				Person person = Person.getAuthenticatedPerson(authenticatedPerson.getExternalId(), authenticatedPerson.getEmail(), oldPassword, false);
				if(person == null){
					messagePassword = "Erreur sur votre ancien mot de passe";
					request.setAttribute("messagePassword", messagePassword);
				}else{
					person.setPassword(Person.hashPassword(newPassword));
					person.persist();

					messagePassword = "Votre mot de passe a été changé";
					request.setAttribute("messagePassword", messagePassword);
				}
			}
			
		}

		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		
	}

}
