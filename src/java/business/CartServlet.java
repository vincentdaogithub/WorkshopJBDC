package business;

import controller.CartActions;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import obj.Mobile;


public class CartServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.log(Level.SEVERE, "{0} does not support GET", this.getServletName());
        throw new ServerException(this.getServletName() + " does not support GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CartActions c = CartActions.convertAction(request.getParameter("c"));

        if (c == null) {
            LOGGER.log(Level.WARNING, "Invalid search type");
            request.setAttribute("isInvalidSearch", true);
            return;
        }

        Object cartSession = request.getSession().getAttribute("cart");
        Map<Mobile, Integer> workingCart;

        if (cartSession == null) {
            Map<Mobile, Integer> newCart = Collections.synchronizedMap(new HashMap<>());
            request.getSession().setAttribute("cart", newCart);
            workingCart = newCart;
        } else {
            workingCart = (Map<Mobile, Integer>) cartSession;
        }

        synchronized (workingCart) {
            switch (c) {
                case ADD:
                    break;

                case  REMOVE:
                    break;

                default:
                    break;
            }
        }
    }
}
