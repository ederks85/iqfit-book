package nl.iqfit.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter that sets a UTF-8 character set to requests that have no character set.
 */
@WebFilter(urlPatterns = { "/*" })
public class CharsetFilter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}
}