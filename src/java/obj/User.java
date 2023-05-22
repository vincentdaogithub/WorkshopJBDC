package obj;

import java.io.Serializable;


public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userID;
    private int password;
    private String fullName;
    private int role;

    public User() {
        this.userID = "";
        this.password = 0;
        this.fullName = "";
        this.role = 0;
    }

    public User(String userID, String fullName, int role) {
        this.userID = userID;
        this.password = 0;
        this.fullName = fullName;
        this.role = role;
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
     * @return the password
     */
    public int getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(int password) {
        this.password = password;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User - [" + userID + "; " + fullName + "; " + role + "]";
    }
}
