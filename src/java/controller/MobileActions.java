package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum MobileActions {

    ADD("add"),
    REMOVE("remove"),
    UPDATE("update");

    private static final Logger LOGGER = Logger.getLogger(MobileActions.class.getName());

    private final String action;

    MobileActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static final MobileActions convertAction(String action) {
        if (action == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (MobileActions a : MobileActions.values()) {
            if (a.getAction().equals(action)) {
                return a;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
