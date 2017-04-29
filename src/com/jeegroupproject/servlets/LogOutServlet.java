package com.jeegroupproject.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.filters.IsAuthenticated;

/**
 * Servlet implementation class LogOutServlet
 */
@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/login";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//TODO find how to invalidate a cookie (if possible because since cookies are specific to client, it's possible that it cannot be deleted from the server)
		response.addCookie(new Cookie(IsAuthenticated.PERSONID_COOKIE_NAME,"-1")); //set cookie to -1 because user can have a negative id ==> invalidate the filter and logs out
		response.addCookie(new Cookie(IsAuthenticated.TOKEN_COOKIE_NAME,""));
		response.sendRedirect(request.getContextPath() + VIEW);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
