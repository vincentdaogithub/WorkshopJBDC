package filter;

import controller.Pages;
import controller.Users;
import dao.CartDAO;
import dao.MobileDAO;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
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
import javax.servlet.http.HttpServletResponse;
import obj.Cart;
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
        HttpServletResponse res = (HttpServletResponse) response;

        Object user = (User) req.getSession().getAttribute("user");
        if (user == null || ((User) user).getRole() != Users.USER.getRoleID()) {
            LOGGER.log(Level.WARNING, "Unauthorized access to user page");
            req.getRequestDispatcher(Pages.WELCOME.getUrl()).forward(request, response);
            return;
        }

        Cart cartDB = CartDAO.getCart(((User) user).getUserID());
        if (cartDB.isEmpty()) {
            req.setAttribute("cart", new ArrayList<>());
        } else {
            List<Map.Entry<Mobile, Integer>> results = new ArrayList<>();
            cartDB.getCartDetails().forEach((e) -> {
                Mobile mobile = MobileDAO.getMobile(e.getMobileID());
                if (mobile == null) {
                    LOGGER.log(Level.SEVERE, "Invalid mobile");
                } else {
                    results.add(new AbstractMap.SimpleEntry<>(mobile, e.getQuantity()));
                }
            });
            req.setAttribute("cart", results);
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
