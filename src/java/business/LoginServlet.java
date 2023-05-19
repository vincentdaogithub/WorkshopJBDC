package business;

import dao.UserDAO;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import obj.User;
import utils.ParseUtils;


public class LoginServlet extends HttpServlet {

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

        String txtUserID = request.getParameter("txtUserID");
        Integer txtPassword = ParseUtils.parseInt(request.getParameter("txtPassword"));
        User user = UserDAO.getUser(txtUserID, txtPassword);

        if (user == null) {
            LOGGER.log(Level.INFO, "Can''t get user [{0}; {1}]", new Object[]{txtUserID, txtPassword});
            request.setAttribute("prevTxtUserID", txtUserID);
            request.setAttribute("prevTxtPassword", txtPassword);
        } else {
            request.getSession().setAttribute("user", user);
        }
    }
}
