package filter;

import controller.Pages;
import controller.Users;
import dao.MobileDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import obj.User;


public class UserFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(UserFilter.class.getName());

    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        User user = (User) req.getSession().getAttribute("user");

        if (user == null || user.getRole() != Users.USER.getRoleID()) {
            LOGGER.log(Level.WARNING, "Unauthorized access to user page");
            req.getRequestDispatcher(Pages.WELCOME.getUrl()).forward(request, response);
            return;
        }

        if (req.getAttribute("mobiles") == null) {
            req.setAttribute("mobiles", MobileDAO.getMobiles());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
