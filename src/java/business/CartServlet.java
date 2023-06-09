package business;

import controller.CartActions;
import dao.CartDAO;
import dao.MobileDAO;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import obj.User;


public class CartServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartActions c = CartActions.convertAction(request.getParameter("c"));

        if (c == null) {
            LOGGER.log(Level.WARNING, "Invalid action");
            return;
        }

        Object cartSession = request.getSession().getAttribute("cart");
        Map<String, Integer> workingCart;

        if (cartSession == null) {
            Map<String, Integer> newCart = Collections.synchronizedMap(new HashMap<>());
            request.getSession().setAttribute("cart", newCart);
            workingCart = newCart;
        } else {
            workingCart = (Map<String, Integer>) cartSession;
        }

        String mid = request.getParameter("mid");
        if (mid.trim().isEmpty() || !MobileDAO.isMobileExist(mid)) {
            LOGGER.log(Level.WARNING, "Invalid mobile ID");
            return;
        }

        synchronized (workingCart) {
            switch (c) {
                case ADD:
                    workingCart.merge(mid, 1, Integer::sum);
                    break;

                case REMOVE:
                    Integer prevQuantity = workingCart.get(mid);
                    if (prevQuantity == null) {
                        LOGGER.log(Level.SEVERE, "Invalid mobile in cart");
                    } else if (prevQuantity <= 0) {
                        workingCart.remove(mid);
                    } else {
                        workingCart.merge(mid, -1, Integer::sum);
                    }
                    break;

                default:
                    LOGGER.log(Level.SEVERE, "Unknown action");
            }

            Object userObj = request.getSession().getAttribute("user");
            if (userObj == null || ((User) userObj).getRole() != 0) {
                LOGGER.log(Level.SEVERE, "Invalid user");
                return;
            }
            String userID = ((User) userObj).getUserID();
            if (!CartDAO.updateCart(userID, workingCart)) {
                LOGGER.log(Level.SEVERE, "Can't update cart to database");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
