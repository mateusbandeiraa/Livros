package control;

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
import javax.servlet.http.HttpSession;

@WebFilter("/adm/*")
public class Filtro implements Filter {

    public Filtro() {

    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		String perf = (String) session.getAttribute("userProf");
		if(perf != null && perf.equalsIgnoreCase("adm")){
		chain.doFilter(request, response);
		}else{
			resp.sendRedirect("../gandalf.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
