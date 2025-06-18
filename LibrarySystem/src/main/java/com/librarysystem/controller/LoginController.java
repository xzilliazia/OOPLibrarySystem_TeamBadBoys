package com.librarysystem.controller;

import com.librarysystem.UI.AdminDashboard;
import com.librarysystem.UI.BookManager;
import com.librarysystem.UI.BorrowDashboard;
import com.librarysystem.UI.StdDashboard;
import com.librarysystem.model.Admin;
import com.librarysystem.model.Student;
import com.librarysystem.model.User;
import com.librarysystem.util.DatabaseConnection;
import com.librarysystem.util.Session;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    public enum Role {
        ADMIN, STUDENT, INVALID
    }

    public void login(String username, String password, Stage currentStage) {
        User user = authenticate(username, password);

        if (user != null) {
            // Set session values
            Session.currentUser = user;
            Session.currentRole = Role.valueOf(user.getRole().toUpperCase());

            switch (Session.currentRole) {
                case ADMIN:
                    loadAdminDashboard(currentStage);
                    break;
                case STUDENT:
                    loadStudentDashboard(currentStage, user);
                    break;
                default:
                    showLoginError();
                    break;
            }
        } else {
            showLoginError();
        }
    }

    private User authenticate(String username, String password) {
        String query = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String uname = rs.getString("username");
                    String roleStr = rs.getString("role");

                    if ("admin".equalsIgnoreCase(roleStr)) {
                        return new Admin(userId, uname);
                    } else if ("student".equalsIgnoreCase(roleStr)) {
                        return new Student(userId, uname);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private void loadAdminDashboard(Stage currentStage) {
        try {
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.start(new Stage());
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadStudentDashboard(Stage currentStage, User currentUser) {
        try {
            StdDashboard dashboard = new StdDashboard();

            // Start StdDashboard on currentStage
            dashboard.start(currentStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Gagal");
        alert.setHeaderText(null);
        alert.setContentText("Username atau password salah!");
        alert.showAndWait();
    }
}
