package filter;

import controller.Pages;
import controller.Users;
import dao.MobileDAO;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import obj.Mobile;
import obj.User;


public class CartFilter implements Filter {

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

        Object cart = req.getSession().getAttribute("cart");

        if (cart == null) {
            Map<String, Integer> newCart = Collections.synchronizedMap(new HashMap<>());
            req.getSession().setAttribute("cart", newCart);
            List<Map.Entry<Mobile, Integer>> emptyList = new ArrayList<>();
            req.setAttribute("cart", emptyList);
        } else {
            Map<String, Integer> workingCart = (Map<String, Integer>) cart;

            synchronized (workingCart) {
                List<Map.Entry<Mobile, Integer>> result = new ArrayList<>();

                for (Map.Entry<String, Integer> i : workingCart.entrySet()) {
                    Mobile addMobile = MobileDAO.getMobile(i.getKey());

                    if (addMobile == null) {
                        LOGGER.log(Level.SEVERE, "Invalid mobile when convert from mobileID");
                        break;
                    }

                    result.add(new AbstractMap.SimpleEntry<>(addMobile, i.getValue()));
                }

                req.setAttribute("cart", result);
            }
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
