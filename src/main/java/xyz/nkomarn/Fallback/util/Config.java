package xyz.nkomarn.Fallback.util;

import xyz.nkomarn.Fallback.Fallback;

import java.util.ArrayList;
import java.util.List;

public class Config {
    /**
     * Fetches a boolean from the configuration
     * if location is not found, <code>false</code> is returned
     *
     * @param location Configuration location of the boolean
     */
    public static boolean getBoolean(final String location) {
        return Fallback.getInstance().getConfig().getBoolean(location, false);
    }

    /**
     * Fetches a string from the configuration
     * if location is not found, <code>empty string</code> is returned
     *
     * @param location Configuration location of the string
     */
    public static String getString(final String location) {
        return Fallback.getInstance().getConfig().getString(location, "");
    }

    /**
     * Fetches an integer from the configuration
     * if location is not found, <code>0</code> is returned
     *
     * @param location Configuration location of the integer
     */
    public static int getInteger(final String location) {
        return Fallback.getInstance().getConfig().getInt(location, 0);
    }

    /**
     * Fetches a double from the configuration
     * if location is not found, <code>0.0</code> is returned
     *
     * @param location Configuration location of the double
     */
    public static double getDouble(final String location) {
        return Fallback.getInstance().getConfig().getDouble(location, 0.0);
    }

    /**
     * Fetches a list from the configuration
     * if location is not found, <code>empty list</code> is returned
     *
     * @param location Configuration location of the list
     */
    public static List<String> getList(final String location) {
        return (List<String>) Fallback.getInstance().getConfig().getList(location, new ArrayList<>());
    }
}
