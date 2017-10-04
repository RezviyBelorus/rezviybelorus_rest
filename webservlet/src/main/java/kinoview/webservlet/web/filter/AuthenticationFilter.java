package kinoview.webservlet.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private static final String USER_FIRST_NAME = "userfName";
    private static final String USER_ID = "userId";
    private static final String LOGIN_REQUEST = "/users/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);
        boolean isUserLoggedIn = session!=null
                && session.getAttribute(USER_FIRST_NAME) != null;

        String loginURI = req.getContextPath() + LOGIN_REQUEST;

        boolean isLoginRequest = req.getRequestURI().equals(loginURI);
        if(isUserLoggedIn || isLoginRequest) {
            filterChain.doFilter(req, resp);
        } else {
            //eto prohod k JSP
//            req.getRequestDispatcher(LOGIN.getFullName()).forward(req, resp);
            resp.sendRedirect("users/login");
        }
    }

    @Override
    public void destroy() {
    }
}
