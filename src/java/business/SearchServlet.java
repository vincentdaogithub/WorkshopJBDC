package business;

import controller.Searches;
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

        Searches s = Searches.convertSearch(request.getParameter("s"));

        if (s == null) {
            LOGGER.log(Level.WARNING, "Invalid search type");
            request.setAttribute("isInvalidSearch", true);
            return;
        }

        Float min = ParseUtils.parseFloat(request.getParameter("numMin"));
        Float max = ParseUtils.parseFloat(request.getParameter("numMax"));

        if (min == null || max == null) {
            LOGGER.log(Level.WARNING, "Null min max");
            request.setAttribute("isInvalidSearch", true);
            return;
        }

        request.setAttribute("mobiles", MobileDAO.getMobilesPriceMinMax(min, max));
    }
}
