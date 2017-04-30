package com.jeegroupproject.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Person;


/**
 * Servlet Filter implementation class IsAuthenticated
 */
@WebFilter("/IsAuthenticated")
public class IsAuthenticated implements Filter {
	
	public static final String DO_LOG_IN = "/login";
	public static final String AUTH_PERSON_ATTR_NAME = "authenticatedPerson";
	public static final String PERSONID_COOKIE_NAME = "id";
	public static final String TOKEN_COOKIE_NAME = "token";
	
    /**
     * Default constructor. 
     */
    public IsAuthenticated() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Returns a person based on given cookies
	 * @param cookies
	 * @return
	 */
	public static Person getAuthenticatedPersonFromCookies(Cookie[] cookies){
		
		Person authenticatedPerson = null;
		Integer id = null;
		String token = null;
		
		if(cookies != null ){
			
            for (Cookie cookie : cookies) {
            	
                //  cookie authentication takes both id and token in account for validation for security
                   if (cookie.getName().equals(PERSONID_COOKIE_NAME)) { // get the value of userId cookie
                   id = Integer.parseInt(cookie.getValue());    
                   
                }
                if (cookie.getName().equals(TOKEN_COOKIE_NAME)) { // get the value of token cookie
                	
                   token = cookie.getValue();         
                   
                }
            }
        }
		
		if(id != null && token != null){
			
            //Try to find a person that matches this id and this token
			authenticatedPerson = Person.getAuthenticatedPersonByToken(id, token);
			
        }

		return authenticatedPerson;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req; // Cast to obtain HttpServletRequest specific methods required to get cookies and context path
		HttpServletResponse response = (HttpServletResponse) res; // Cast to obtain HttpServletResponse specific methods required to get cookies 
		Cookie[] cookies = request.getCookies(); // get all cookies for the current domain (IE, those that are sent by client)
		
		Person authenticatedPerson = getAuthenticatedPersonFromCookies(cookies);
		
		if(authenticatedPerson != null){
			
			 req.setAttribute(AUTH_PERSON_ATTR_NAME, authenticatedPerson);
             chain.doFilter(request, response); //let the person go to the restricted main page
             
		}else{
			
			response.sendRedirect(request.getContextPath() + DO_LOG_IN); // redirects to login page
			return;
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
