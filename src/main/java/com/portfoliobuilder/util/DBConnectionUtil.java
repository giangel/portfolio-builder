package com.portfoliobuilder.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {

    private static final Properties PROPS = new Properties();
    private static boolean driverLoaded = false;

    static {
        loadProperties();
        loadDriver();
    }

    private DBConnectionUtil() {
    }

    private static void loadProperties() {
        try (InputStream input = DBConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                PROPS.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties: " + e.getMessage(), e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            driverLoaded = true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found on classpath.", e);
        }
    }

    private static String resolve(String envKey, String propsKey) {
        String value = System.getenv(envKey);
        return (value != null && !value.isEmpty()) ? value : PROPS.getProperty(propsKey);
    }

    public static Connection getConnection() throws SQLException {
        if (!driverLoaded) {
            loadDriver();
        }
        String url = resolve("DB_URL", "db.url");
        String username = resolve("DB_USERNAME", "db.username");
        String password = resolve("DB_PASSWORD", "db.password");
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // Intentionally ignored, closing on cleanup should never mask the original error.
            }
        }
    }
}