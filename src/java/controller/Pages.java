package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum Pages {

    WELCOME("welcome", "/index.jsp"),
    USER("user", "/user.jsp"),
    CART("cart", "/cart.jsp");

    private static final Logger LOGGER = Logger.getLogger(Pages.class.getName());

    private final String page;
    private final String url;

    Pages(String page, String url) {
        this.page = page;
        this.url = url;
    }

    public String getPage() {
        return page;
    }

    public String getUrl() {
        return url;
    }

    public static final Pages convertPage(String page) {
        if (page == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (Pages p : Pages.values()) {
            if (p.getPage().equals(page)) {
                return p;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
