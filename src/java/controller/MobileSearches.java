package controller;

import java.util.logging.Level;
import java.util.logging.Logger;


public enum MobileSearches {

    PRICE_MIN_MAX("price-min-max"),
    ID("id"),
    NAME("name");

    private static final Logger LOGGER = Logger.getLogger(MobileSearches.class.getName());

    private final String search;

    MobileSearches(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public static final MobileSearches convertSearch(String search) {
        if (search == null) {
            LOGGER.log(Level.INFO, "Null input");
            return null;
        }

        for (MobileSearches s : MobileSearches.values()) {
            if (s.getSearch().equals(search)) {
                return s;
            }
        }

        LOGGER.log(Level.INFO, "Invalid input");
        return null;
    }
}
