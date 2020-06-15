package xyz.nkomarn.Backfire.util;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.nkomarn.Backfire.Backfire;

public class Config {
    /**
     * Fetches the FileConfiguration instance.
     * @return FileConfiguration instance.
     */
    public static FileConfiguration getConfig() {
        return Backfire.BACKFIRE.getConfig();
    }

    /**
     * Fetches a boolean from the configuration
     * if location is not found, false is returned
     * @param location Configuration location of the boolean
     */
    public static boolean getBoolean(final String location) {
        return getConfig().getBoolean(location, false);
    }

    /**
     * Fetches a string from the configuration
     * if location is not found, empty string is returned
     * @param location Configuration location of the string
     */
    public static String getString(final String location) {
        return getConfig().getString(location, "");
    }

    /**
     * Fetches an integer from the configuration
     * if location is not found, 0 is returned
     * @param location Configuration location of the integer
     */
    public static int getInteger(final String location) {
        return getConfig().getInt(location, 0);
    }

    /**
     * Fetches a double from the configuration
     * if location is not found, 0.0 is returned
     * @param location Configuration location of the double
     */
    public static double getDouble(final String location) {
        return getConfig().getDouble(location, 0.0);
    }
}
