package obj;

import java.io.Serializable;


public class CartDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mobileID;
    private int quantity;

    public CartDetail() {
        this.mobileID = "";
        this.quantity = 0;
    }

    public CartDetail(String mobileID, int quantity) {
        this.mobileID = mobileID;
        this.quantity = quantity;
    }

    /**
     * @return the mobileID
     */
    public String getMobileID() {
        return mobileID;
    }

    /**
     * @param mobileID the mobileID to set
     */
    public void setMobileID(String mobileID) {
        this.mobileID = mobileID;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
