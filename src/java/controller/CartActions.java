package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum CartActions {

    ADD("add"),
    REMOVE("remove");

    private static final Logger LOGGER = Logger.getLogger(Searches.class.getName());

    private final String action;

    CartActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static final CartActions convertAction(String action) {
        if (action == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (CartActions a : CartActions.values()) {
            if (a.getAction().equals(action)) {
                return a;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
