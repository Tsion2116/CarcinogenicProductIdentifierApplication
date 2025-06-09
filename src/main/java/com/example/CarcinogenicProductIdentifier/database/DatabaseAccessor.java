package com.example.CarcinogenicProductIdentifier.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccessor {
    private static final String DB_URL = "jdbc:sqlite:carcinogenic_products.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(DB_URL);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createNewDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                // Database created successfully
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTables() {
        // SQL statement for creating a new users table
        String sqlUsers = "CREATE TABLE IF NOT EXISTS users (\n" +
                          "    id integer PRIMARY KEY,\n" +
                          "    username text NOT NULL UNIQUE,\n" +
                          "    password text NOT NULL\n" +
                          ");";

        // SQL statement for creating a new sessions table
        String sqlSessions = "CREATE TABLE IF NOT EXISTS sessions (\n" +
                             "    id integer PRIMARY KEY,\n" +
                             "    user_id integer NOT NULL,\n" +
                             "    user_input text,\n" +
                             "    gemini_response text,\n" +
                             "    FOREIGN KEY (user_id) REFERENCES users(id)\n" +
                             ");";

        try (Connection conn = connect();
             java.sql.Statement stmt = conn.createStatement()) {
            // create new tables
            stmt.execute(sqlUsers);
            stmt.execute(sqlSessions);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String[] getUserCredentials(String username) {
        String sql = "SELECT username, password FROM users WHERE username = ?";
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String user = rs.getString("username");
                    String pass = rs.getString("password");
                    return new String[]{user, pass};
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        createNewDatabase();
        createNewTables();
    }
}