package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Cart;
import obj.CartDetail;
import utils.DBUtils;
import utils.ParseUtils;


public class CartDAO {

    private static final Logger LOGGER = Logger.getLogger(CartDAO.class.getName());

    private static final String GET_CART_ID = "SELECT * FROM tbl_Cart WHERE userID = ?";

    private static final String GET_CART = "SELECT a.cartID, a.userID, b.mobileID, b.quantity "
            + "FROM tbl_Cart AS a INNER JOIN tbl_CartDetail AS b "
            + "ON a.userID = ? AND a.cartID = b.cartID";

    private static final String GET_LAST_CART_ID = "SELECT MAX(cartID) AS cartID"
            + " FROM tbl_Cart";

    private static final String REMOVE_CART = "DELETE FROM tbl_Cart "
            + "WHERE cartID = ?";

    private static final String INSERT_CART = "INSERT INTO tbl_Cart "
            + "VALUES (?, ?)";

    private static final String INSER_CART_DETAIL = "INSERT INTO tbl_CartDetail "
            + "VALUES (?, ?, ?)";

    public static final String getCartID(String userID) {
        if (userID == null) {
            LOGGER.log(Level.SEVERE, "Null user id");
            return null;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (
                    PreparedStatement pst = con.prepareStatement(GET_CART_ID);
            ) {
                pst.setString(1, userID);
                ResultSet result = pst.executeQuery();
                if (!result.next()) {
                    return null;
                }
                return result.getString("cartID");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public static final Cart getCart(String userID) {
        if (userID == null) {
            LOGGER.log(Level.SEVERE, "Null user ID");
            return new Cart();
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            try (
                    PreparedStatement pst = con.prepareStatement(GET_CART);
            ) {
                pst.setString(1, userID);
                ResultSet results = pst.executeQuery();
                Cart cart = new Cart();
                cart.setUserID(userID);
                if (!results.next()) {
                    return cart;
                }

                cart.setCartID(results.getString("cartID"));
                List<CartDetail> cartdDetails = new ArrayList<>();
                do {
                    cartdDetails.add(new CartDetail(
                            results.getString("mobileID"),
                            results.getInt("quantity")
                    ));
                } while (results.next());
                cart.setCartDetails(cartdDetails);
                return cart;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return new Cart();
    }

    public static final boolean updateCart(String userID, Map<String, Integer> cart) {
        if (userID == null || cart == null || cart.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid parameters");
            return false;
        }

        try (
                Connection con = DBUtils.getConnection();
        ) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (
                    PreparedStatement pst = con.prepareStatement(GET_CART_ID)
            ) {
                pst.setString(1, userID);
                ResultSet result = pst.executeQuery();
                String cartID;
                if (result.next()) {
                    cartID = result.getString("cartID");
                    try (
                            PreparedStatement pstTmp = con.prepareStatement(REMOVE_CART);
                    ) {
                        pstTmp.setString(1, cartID);
                        if (pstTmp.executeUpdate() != 1) {
                            throw new SQLException("Multiple cart");
                        }
                        con.commit();
                    }
                } else {
                    try (
                            PreparedStatement pstTmp = con.prepareStatement(GET_LAST_CART_ID);
                    ) {
                        ResultSet resultTmp = pstTmp.executeQuery();
                        if (resultTmp.next()) {
                            cartID = resultTmp.getString("cartID");
                            Integer maxCartNum = ParseUtils.parseInt(
                                    cartID.replaceAll("[^0-9]", "")
                            );
                            if (maxCartNum == null || maxCartNum < 0 || maxCartNum + 1 > 9999) {
                                throw new Exception("Can't get max cart number");
                            }
                            cartID = String.format("C%04d", maxCartNum + 1);
                        } else {
                            throw new Exception("Can't get max cart number");
                        }
                    }
                }

                try (
                        PreparedStatement insertCartPst = con.prepareStatement(INSERT_CART);
                ) {
                    insertCartPst.setString(1, cartID);
                    insertCartPst.setString(2, userID);
                    if (insertCartPst.executeUpdate() != 1) {
                        throw new SQLException("Can't insert cart");
                    }
                    con.commit();
                }

                try (
                        PreparedStatement insertCartDetailSt = con.prepareStatement(INSER_CART_DETAIL);
                ) {
                    insertCartDetailSt.setString(1, cartID);

                    synchronized (cart) {
                        for (Map.Entry<String, Integer> e : cart.entrySet()) {
                            insertCartDetailSt.setString(2, e.getKey());
                            insertCartDetailSt.setInt(3, e.getValue());
                            if (insertCartDetailSt.executeUpdate() != 1) {
                                throw new SQLException("Can't insert cart detail");
                            }
                        }
                        con.commit();
                    }
                }
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
