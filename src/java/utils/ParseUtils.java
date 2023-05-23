package utils;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ParseUtils {

    private static final Logger LOGGER = Logger.getLogger(ParseUtils.class.getName());

    public static final Integer parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Conversion of invalid number");
            return null;
        }
    }

    public static final Float parseFloat(String input) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Conversion of invalid number");
            return null;
        }
    }
}
