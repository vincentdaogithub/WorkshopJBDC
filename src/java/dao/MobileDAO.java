package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Mobile;
import utils.DBUtils;


public class MobileDAO {

    private static final Logger LOGGER = Logger.getLogger(MobileDAO.class.getName());

    private static final String GET_MOBILES = "SELECT * FROM tbl_Mobile";

    public static final List<Mobile> getMobiles() {
        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(GET_MOBILES)
            ) {
                ResultSet results = pst.executeQuery();
                List<Mobile> mobiles = new ArrayList<>();

                while (results.next()) {
                    mobiles.add(new Mobile(results.getString("mobileID"),
                            results.getString("description"),
                            results.getFloat("price"),
                            results.getString("mobileName"),
                            results.getInt("yearOfProduction"),
                            results.getInt("quantity"),
                            results.getBoolean("notSale")
                    ));
                }

                return mobiles;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't get mobiles");
            return new ArrayList<>();
        }
    }
}
