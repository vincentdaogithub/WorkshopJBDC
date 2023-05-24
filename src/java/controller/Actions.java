package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum Actions {

    LOGIN("login", "/LoginServlet"),
    SEARCH("search", "/SearchServlet"),
    CART("cart", "/CartServlet"),
    MOBILE("mobile", "/MobileServlet"),
    CREATE("create", "/CreateServlet");

    private static final Logger LOGGER = Logger.getLogger(Actions.class.getName());

    private final String action;
    private final String url;

    Actions(String action, String url) {
        this.action = action;
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }

    public static final Actions convertAction(String action) {
        if (action == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (Actions a : Actions.values()) {
            if (a.getAction().equals(action)) {
                return a;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
