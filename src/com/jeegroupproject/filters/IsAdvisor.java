package com.jeegroupproject.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeegroupproject.beans.Person;

/**
 * Servlet Filter implementation class IsAdvisor
 */
@WebFilter("/IsAdvisor")
public class IsAdvisor implements Filter {
	public static final String GO_MAIN_AUTH = "/authenticated/main";

    /**
     * Default constructor. 
     */
    public IsAdvisor() {
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
		
		HttpServletRequest request = (HttpServletRequest) req; // Cast to obtain HttpServletRequest specific methods required to get context path
		HttpServletResponse response = (HttpServletResponse) res; // Cast to obtain HttpServletResponse specific methods required to get context path
		
		Person authenticatedPerson = (Person) request.getAttribute(IsAuthenticated.AUTH_PERSON_ATTR_NAME); // returns the authenticated person set by the filter
		
		if(authenticatedPerson.isAdvisor()){
			
            chain.doFilter(request, response); //let the person go to the restricted main advisor page
            
		}else{
			
			response.sendRedirect(request.getContextPath() + GO_MAIN_AUTH); // redirects to main authenticated page
			
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
