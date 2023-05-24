package business;

import dao.MobileDAO;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import obj.Mobile;
import utils.ParseUtils;


public class CreateServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, "{0} does not support GET", this.getServletName());
        throw new ServerException(this.getServletName() + " does not support GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mobileName = request.getParameter("txtMobileName");
        if (mobileName.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid mobile name");
            return;
        }

        String description = request.getParameter("txtDescription");
        if (description.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid description");
            return;
        }

        Integer yearOfProduction = ParseUtils.parseInt(request.getParameter("itYearOfProduction"));
        if (yearOfProduction == null || yearOfProduction < 0 || yearOfProduction > 2023) {
            LOGGER.log(Level.SEVERE, "Invalid year of production");
            return;
        }

        Float price = ParseUtils.parseFloat(request.getParameter("flPrice"));
        if (price == null || price < 0) {
            LOGGER.log(Level.SEVERE, "Invalid price");
            return;
        }

        Integer quantity = ParseUtils.parseInt(request.getParameter("itQuantity"));
        if (quantity == null || quantity < 0) {
            LOGGER.log(Level.SEVERE, "Invalid quantity");
            return;
        }

        String txtNotSale = request.getParameter("blNotSale");
        if (txtNotSale == null || !(txtNotSale.equals("true") || txtNotSale.equals("false"))) {
            LOGGER.log(Level.SEVERE, "Invalid notSale");
            return;
        }
        boolean notSale = Boolean.parseBoolean(txtNotSale);

        Mobile mobile = new Mobile("", description, price, mobileName,
                yearOfProduction, quantity, notSale);
        if (!MobileDAO.insertMobile(mobile)) {
            LOGGER.log(Level.SEVERE, "Can't insert mobile");
        }
    }
}
