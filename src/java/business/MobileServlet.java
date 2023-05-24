package business;

import controller.MobileActions;
import dao.MobileDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import obj.Mobile;
import utils.ParseUtils;


public class MobileServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MobileActions m = MobileActions.convertAction(request.getParameter("m"));
        if (m == null) {
            LOGGER.log(Level.WARNING, "Invalid action");
            return;
        }

        String mid = request.getParameter("mid");
        if (mid.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid mobile ID");
            return;
        }

        switch (m) {
            case ADD:
                LOGGER.log(Level.WARNING, "Unimplemeted");
                break;

            case REMOVE:
                if (!MobileDAO.removeMobile(mid)) {
                    LOGGER.log(Level.SEVERE, "Can''t remove mobile {0}", mid);
                }
                break;

            case UPDATE:
                processUpdate(request, request, mid);
                break;

            default:
                LOGGER.log(Level.SEVERE, "Unknown action");
        }
    }

    private void processUpdate(HttpServletRequest req, HttpServletRequest res, String mid) {
        Mobile mobile = MobileDAO.getMobile(mid);
        if (mobile == null) {
            LOGGER.log(Level.SEVERE, "Can't get mobile to update");
            return;
        }

        Float uptPrice = ParseUtils.parseFloat(req.getParameter("flPrice"));
        if (uptPrice == null || uptPrice < 0) {
            LOGGER.log(Level.SEVERE, "Invalid price");
            return;
        }

        String uptDescription = req.getParameter("txtDescription");
        if (uptDescription.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid description");
            return;
        }

        Integer uptQuantity = ParseUtils.parseInt(req.getParameter("itQuantity"));
        if (uptQuantity == null || uptQuantity < 0) {
            LOGGER.log(Level.SEVERE, "Invalid quantity");
            return;
        }

        String txtNotSale = req.getParameter("blNotSale");
        if (txtNotSale == null || !(txtNotSale.equals("true") || txtNotSale.equals("false"))) {
            LOGGER.log(Level.SEVERE, "Invalid notSale");
            return;
        }
        boolean uptNotSale = Boolean.parseBoolean(txtNotSale);

        mobile.setPrice(uptPrice);
        mobile.setDescription(uptDescription);
        mobile.setQuantity(uptQuantity);
        mobile.setNotSale(uptNotSale);

        if (!MobileDAO.updateMobile(mobile)) {
            LOGGER.log(Level.SEVERE, "Can't update mobile");
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
