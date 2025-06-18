package com.librarysystem.service;

import com.librarysystem.model.Book;
import com.librarysystem.model.Student;
import com.librarysystem.model.User;
import com.librarysystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserUtil {
    public static User findUserById(int userId) {
        String sql = "SELECT id, username, major FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("username")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateUserProfile(User user) {
        String sql = "UPDATE users SET username = ?, major = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getMajor());
            stmt.setInt(3, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User updatedUser) {
        String sql = "UPDATE users SET username = ?, major = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedUser.getUsername());
            pstmt.setString(2, updatedUser.getMajor());
            pstmt.setInt(3, updatedUser.getUserId());
            pstmt.executeUpdate();

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User Updated Successfully.");
            } else {
                System.out.println("User ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUsers() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                userList.add(new User(
                        rs.getString("username"),
                        rs.getString("major")
                        ) {
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}

