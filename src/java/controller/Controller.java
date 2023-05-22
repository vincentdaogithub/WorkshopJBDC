package controller;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class Controller implements Filter {

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Actions a = Actions.convertAction(req.getParameter("a"));

        if (a != null) {
            req.getRequestDispatcher(a.getUrl()).include(request, response);
        }

        chain.doFilter(request, response);

        Pages p = Pages.convertPage(req.getParameter("p"));

        if (p != null) {
            req.getRequestDispatcher(p.getUrl()).forward(request, response);
        } else {
            Pages pageRequested = (Pages) req.getAttribute("p");
            
            if (pageRequested != null) {
                req.getRequestDispatcher(pageRequested.getUrl()).forward(request, response);
            } else {
                req.getRequestDispatcher(Pages.WELCOME.getUrl()).forward(request, response);
            }
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
