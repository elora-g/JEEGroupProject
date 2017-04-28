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

/**
 * Servlet Filter implementation class IsLoggedIn
 */
@WebFilter("/IsLoggedIn")
public class IsLoggedIn implements Filter {
	public static final String DO_LOG_IN = "/login";
	
    /**
     * Default constructor. 
     */
    public IsLoggedIn() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req; // Syntaxe pour caster
		HttpServletResponse response = (HttpServletResponse) res;
		Cookie[] cookies = request.getCookies(); // get all cookies for the current domain (IE, those that are sent by client)
		
		boolean needsLoginForm = true;
		
		// create variables int clientid and string token default to null
		if(cookies != null ){
			for (Cookie cookie : cookies) {
				//TODO : change cookie authentication to take into account the token
			   if (cookie.getName().equals("loggedIn")) { // replace loggedIn by clientId
				   if(cookie.getValue().equals("1")){
					    needsLoginForm = false; // 
					    //TODO if authenticated, place the authenticated user in request param. It will most likely be used everywhere
				   }
			    }
			   /** for both cookie find it in cookies and set the value the variables created above
			    * 
			    * if (cookie.getName().equals("loggedIn")) { cookie.getName().equals("clientid") then client id = cookie.getValue (do for both cookies)
				   

				   }
			    }
			   */
			   /**
			    * test that client id and token are not null and then we call getAuthenticatedUserbyToken (returns authenticatedUser)
			    * 
			    * 
			    * //TODO if authenticated, place the authenticated user in request param req.setAttribute("authenticatedUser", authenticatedUser). It will most likely be used everywhere
			    */
			}
		}
		
		if(needsLoginForm){
			response.sendRedirect(request.getContextPath() + DO_LOG_IN);
		}else{
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
