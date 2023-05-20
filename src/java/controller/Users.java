package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum Users {

    USER("user", 0),
    MANAGER("manager", 1),
    STAFF("staff", 2);

    private static final Logger LOGGER = Logger.getLogger(Pages.class.getName());

    private final String role;
    private final int roleID;

    Users(String role, int roleID) {
        this.role = role;
        this.roleID = roleID;
    }

    public String getRole() {
        return role;
    }

    public int getRoleID() {
        return roleID;
    }

    public static final Users convertRole(Integer roleID) {
        if (roleID == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (Users u : Users.values()) {
            if (Integer.compare(u.getRoleID(), roleID) == 0) {
                return u;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
