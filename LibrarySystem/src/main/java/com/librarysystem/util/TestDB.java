package com.librarysystem.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");

                // View data from users table
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");

                System.out.println("ID\t\t\t\t\tUsername\t\tRole");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String username = rs.getString("username");
                    String role = rs.getString("role");
                    System.out.println(id + "\t" + username + "\t\t" + role);
                }

                rs.close();
                stmt.close();
            } else {
                System.out.println("Failed to connect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
