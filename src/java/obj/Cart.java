package obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cartID;
    private String userID;
    private List<CartDetail> cartDetails;

    public Cart() {
        this.cartID = "";
        this.userID = "";
        this.cartDetails = new ArrayList<>();
    }

    public Cart(String cartID, String userID, List<CartDetail> cartDetails) {
        this.cartID = cartID;
        this.userID = userID;
        this.cartDetails = cartDetails;
    }

    /**
     * @return the cartID
     */
    public String getCartID() {
        return cartID;
    }

    /**
     * @param cartID the cartID to set
     */
    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the cartDetails
     */
    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    /**
     * @param cartDetails the cartDetails to set
     */
    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public final boolean isEmpty() {
        return this.cartDetails.isEmpty();
    }
}
