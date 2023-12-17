package com.jdbcfx.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    // connecting database.properties file
    private static final String PROPERTIES_FILE = "/com/jdbcfx/database.properties";
    private static Properties properties = new Properties();

    static {
        try { // read and load properties
            InputStream inputStream = DatabaseConfig.class.getResourceAsStream(PROPERTIES_FILE);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters for properties
    public static String getDriver() {
        return properties.getProperty("db.driver");
    }
    public static String getUrl() {
        return properties.getProperty("db.url");
    }
    public static String getUser() {
        return properties.getProperty("db.user");
    }
    public static String getPassword() {
        return properties.getProperty("db.password");
    }

}
