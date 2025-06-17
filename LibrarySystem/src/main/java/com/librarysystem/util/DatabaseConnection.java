package com.librarysystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/library";
        String user = "postgres";
        String password = "helboy05";
        try {
            Class.forName("org.postgresql.Driver"); // optional but helpful
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
