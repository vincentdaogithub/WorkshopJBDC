package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Mobile;
import utils.DBUtils;
import utils.ParseUtils;


public class MobileDAO {

    private static final Logger LOGGER = Logger.getLogger(MobileDAO.class.getName());

    private static final String GET_MOBILES = "SELECT * FROM tbl_Mobile";

    private static final String SEARCH_MOBILE_PRICE = "SELECT * FROM tbl_Mobile "
            + "WHERE price BETWEEN ? AND ?";

    private static final String GET_MOBILE = "SELECT * FROM tbl_Mobile "
            + "WHERE mobileID = ?";

    private static final String REMOVE_MOBILE = "DELETE FROM tbl_Mobile "
            + "WHERE mobileID = ?";

    private static final String UPDATE_MOBILE = "UPDATE tbl_Mobile "
            + "SET price = ?, description = ?, quantity = ?, notSale = ? "
            + "WHERE mobileID = ?";

    private static final String SEARCH_MOBILE_ID = "SELECT * FROM tbl_Mobile "
            + "WHERE mobileID LIKE ?";

    private static final String SEARCH_MOBILE_NAME = "SELECT * FROM tbl_Mobile "
            + "WHERE mobileName LIKE ?";

    private static final String INSERT_MOBILE = "INSERT INTO tbl_Mobile "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_LAST_MOBILE_ID = "SELECT MAX(mobileID) AS mobileID"
            + " FROM tbl_Mobile";

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
            LOGGER.log(Level.SEVERE, e.getMessage());
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
            LOGGER.log(Level.SEVERE, e.getMessage());
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
                    PreparedStatement pst = con.prepareStatement(SEARCH_MOBILE_PRICE)
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
            LOGGER.log(Level.SEVERE, e.getMessage());
            return new ArrayList<>();
        }
    }

    public static final List<Mobile> getMobilesID(String mobileID) {
        if (mobileID == null) {
            LOGGER.log(Level.WARNING, "Null parameters");
            return new ArrayList<>();
        }
        mobileID = "%" + mobileID + "%";

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(SEARCH_MOBILE_ID)
            ) {
                pst.setString(1, mobileID);
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
            LOGGER.log(Level.SEVERE, e.getMessage());
            return new ArrayList<>();
        }
    }

    public static final List<Mobile> getMobilesName(String mobileName) {
        if (mobileName == null) {
            LOGGER.log(Level.WARNING, "Null parameters");
            return new ArrayList<>();
        }
        mobileName = "%" + mobileName + "%";

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(SEARCH_MOBILE_NAME)
            ) {
                pst.setString(1, mobileName);
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
            LOGGER.log(Level.SEVERE, e.getMessage());
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

    public static final boolean removeMobile(String mobileID) {
        if (mobileID == null) {
            LOGGER.log(Level.SEVERE, "Null mobileID at {0}", MobileDAO.class.getName());
            return false;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            try (
                    PreparedStatement pst = con.prepareStatement(REMOVE_MOBILE)
            ) {
                pst.setString(1, mobileID);
                if (pst.executeUpdate() != 1) {
                    throw new SQLException("Invalid removal");
                }
                con.commit();
                return true;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
                con.rollback();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return false;
    }

    public static final boolean updateMobile(Mobile mobile) {
        if (mobile == null) {
            LOGGER.log(Level.SEVERE, "Null mobile");
            return false;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            try (
                    PreparedStatement pst = con.prepareStatement(UPDATE_MOBILE)
            ) {
                pst.setFloat(1, mobile.getPrice());
                pst.setString(2, mobile.getDescription());
                pst.setInt(3, mobile.getQuantity());
                pst.setBoolean(4, mobile.isNotSale());
                pst.setString(5, mobile.getMobileID());

                if (pst.executeUpdate() != 1) {
                    throw new SQLException("Invalid removal");
                }
                con.commit();
                return true;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
                con.rollback();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    public static final boolean insertMobile(Mobile mobile) {
        if (mobile == null) {
            LOGGER.log(Level.SEVERE, "Null mobile");
            return false;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            Integer mobileID = 0;

            try (
                    PreparedStatement pst = con.prepareStatement(GET_LAST_MOBILE_ID);
            ) {
                ResultSet result = pst.executeQuery();
                if (result.next()) {
                    String maxMobileID = result.getString("mobileID");
                    mobileID = ParseUtils.parseInt(maxMobileID
                            .replaceAll("[^0-9]", ""));
                    if (mobileID == null || mobileID + 1 > 9999) {
                        LOGGER.log(Level.SEVERE, "Can't get max mobile ID");
                        return false;
                    }
                    mobileID++;
                }
            }

            try (
                    PreparedStatement pst = con.prepareStatement(INSERT_MOBILE)
            ) {
                pst.setString(1, String.format("M%04d", mobileID));
                pst.setString(2, mobile.getDescription());
                pst.setFloat(3, mobile.getPrice());
                pst.setString(4, mobile.getMobileName());
                pst.setInt(5, mobile.getYearOfProduction());
                pst.setInt(6, mobile.getQuantity());
                pst.setBoolean(7, mobile.isNotSale());

                if (pst.executeUpdate() != 1) {
                    throw new SQLException("Invalid insert");
                }
                con.commit();
                return true;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
                con.rollback();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }
}
