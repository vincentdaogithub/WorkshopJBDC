package business;

import controller.MobileSearches;
import dao.MobileDAO;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ParseUtils;


public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.log(Level.SEVERE, "{0} does not support GET", this.getServletName());
        throw new ServerException(this.getServletName() + " does not support GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MobileSearches s = MobileSearches.convertSearch(request.getParameter("s"));
        if (s == null) {
            LOGGER.log(Level.WARNING, "Invalid search type");
            request.setAttribute("isInvalidSearch", true);
            return;
        }

        switch (s) {
            case PRICE_MIN_MAX:
                searchPrice(request);
                break;

            case ID:
                searchMobileID(request);
                break;

            case NAME:
                searchMobileName(request);
                break;

            default:
                LOGGER.log(Level.SEVERE, "Invalid action");
        }
    }

    private void searchPrice(HttpServletRequest req) {
        Float min = ParseUtils.parseFloat(req.getParameter("numMin"));
        Float max = ParseUtils.parseFloat(req.getParameter("numMax"));
        if (min == null || max == null) {
            LOGGER.log(Level.WARNING, "Invalid min max");
            return;
        }
        req.setAttribute("mobiles", MobileDAO.getMobilesPriceMinMax(min, max));
    }

    private void searchMobileID(HttpServletRequest req) {
        String mobileID = req.getParameter("txtMobileID");
        req.setAttribute("mobiles", MobileDAO.getMobilesID(mobileID));
    }

    private void searchMobileName(HttpServletRequest req) {
        String mobileName = req.getParameter("txtMobileName");
        req.setAttribute("mobiles", MobileDAO.getMobilesName(mobileName));
    }
}
