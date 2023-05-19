package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.User;
import utils.DBUtils;


public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    private static final String GET_USER = "SELECT * FROM tbl_User "
            + "WHERE userID = ? AND password = ?";

    public static final User getUser(String userID, Integer password) {
        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(
                            GET_USER,
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );
            ) {
                pst.setString(1, userID);
                pst.setInt(2, password);

                try (
                        ResultSet result = pst.executeQuery();
                ) {
                    if (result.next() && !result.isLast()) {
                        throw new Exception("Return more than one User");
                    }

                    User user = new User(
                            result.getString("userID"),
                            result.getInt("password"),
                            result.getString("fullName"),
                            result.getInt("role")
                    );

                    result.close();
                    return user;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return null;
    }
}
