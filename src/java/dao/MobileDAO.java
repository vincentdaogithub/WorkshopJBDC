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

    private static final String GET_MOBILES_MIN_MAX = "SELECT * FROM tbl_Mobile "
            + "WHERE price BETWEEN ? AND ?";

    private static final String GET_MOBILE = "SELECT * FROM tbl_Mobile "
            + "WHERE mobileID = ?";

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

    public static final Mobile getMobile(String mobileID) {
        if (mobileID == null) {
            LOGGER.log(Level.SEVERE, "Null mobileID at {0}", MobileDAO.class.getName());
            return null;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(GET_MOBILE)
            ) {
                pst.setString(1, mobileID);
                ResultSet result = pst.executeQuery();

                if (result.next()) {
                    return new Mobile(result.getString("mobileID"),
                            result.getString("description"),
                            result.getFloat("price"),
                            result.getString("mobileName"),
                            result.getInt("yearOfProduction"),
                            result.getInt("quantity"),
                            result.getBoolean("notSale")
                    );
                }

                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't get mobiles");
            return null;
        }
    }

    public static final List<Mobile> getMobilesPriceMinMax(Float min, Float max) {
        if (min == null || max == null) {
            LOGGER.log(Level.WARNING, "Null parameters");
            return new ArrayList<>();
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(GET_MOBILES_MIN_MAX)
            ) {
                pst.setFloat(1, min);
                pst.setFloat(2, max);
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

    public static final boolean isMobileExist(String mobileID) {
        if (mobileID == null) {
            LOGGER.log(Level.SEVERE, "Null mobileID at {0}", MobileDAO.class.getName());
            return false;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(GET_MOBILE)
            ) {
                pst.setString(1, mobileID);
                ResultSet results = pst.executeQuery();

                return results.next();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }
}
