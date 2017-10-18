package edu.colostate.cs.cs414.enigma.filter;

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

/**
 * Servlet Filter implementation class TrainerFilter
 */
@WebFilter("/TrainerFilter")
public class TrainerFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if(req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;

			String level = (String) request.getSession(false).getAttribute("level");
			if(level == null) {
				if(!request.getRequestURI().contains("index")) {
					response.sendRedirect(request.getContextPath() + "/index.jsp");
					return;
				}	
			}
			
			if(!(level.equals("TRAINER") || level.equals("ADMIN"))) {
				if(!request.getRequestURI().contains("index")) {
					response.sendRedirect(request.getContextPath() + "/index.jsp");
					return;
				}	
			}
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
