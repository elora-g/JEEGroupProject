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

import com.jeegroupproject.beans.User;

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

        
        if(userId == null || token == null){ //missing the cookies, one needs to login.
            response.sendRedirect(request.getContextPath() + DO_LOG_IN);
        }else{
            //Try to find a user that matches that 
            User authenticatedUser = User.getAuthenticatedUserByToken(userId, token);
            if(authenticatedUser == null){ //could not find a user with that tocken and id
                response.sendRedirect(request.getContextPath() + DO_LOG_IN); // go login
            }else{//we found the user
                // enhance request with the found user. It will most likely be used everywhere
                req.setAttribute("authenticatedUser", authenticatedUser);
                chain.doFilter(request, response);
            }   
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
