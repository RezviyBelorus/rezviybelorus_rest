package kinoview.webservlet.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainFilter implements Filter {
    private static final String MAIN_PAGE_REQUEST = "/main";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String mainURI = req.getContextPath() + MAIN_PAGE_REQUEST;
        if (req.getRequestURI().equals(mainURI) || session != null) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("main");
        }
    }

    @Override
    public void destroy() {

    }
}
