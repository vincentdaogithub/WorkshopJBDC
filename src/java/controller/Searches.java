package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum Searches {

    PRICE_MIN_MAX("price-min-max");

    private static final Logger LOGGER = Logger.getLogger(Searches.class.getName());

    private final String search;

    Searches(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public static final Searches convertSearch(String search) {
        if (search == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (Searches s : Searches.values()) {
            if (s.getSearch().equals(search)) {
                return s;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
