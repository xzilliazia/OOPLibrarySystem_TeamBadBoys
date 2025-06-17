package com.librarysystem.controller;

import com.librarysystem.UI.BookManager;
import com.librarysystem.util.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    public enum Role {
        ADMIN, MAHASISWA, INVALID
    }

    public void login(String username, String password, Stage currentStage) {
        Role role = authenticate(username, password);

        switch (role) {
            case ADMIN:
                loadAdminDashboard(currentStage);  // CALL YOUR JAVA CLASS DASHBOARD
                break;
            case MAHASISWA:
                loadStudentDashboard(currentStage);  // CALL FXML IF NEEDED
                break;
            case INVALID:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Gagal");
                alert.setHeaderText(null);
                alert.setContentText("Username atau password salah!");
                alert.showAndWait();
                break;
        }
    }

    private Role authenticate(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String roleStr = rs.getString("role");
                    if ("admin".equalsIgnoreCase(roleStr)) {
                        return Role.ADMIN;
                    } else if ("student".equalsIgnoreCase(roleStr)) {
                        return Role.MAHASISWA;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Role.INVALID;
    }

    private void loadAdminDashboard(Stage currentStage) {
        try {
            BookManager manager = new BookManager();
            Scene scene = manager.createScene();

            Stage stage = new Stage();
            stage.setTitle("Admin Dashboard");
            stage.setScene(scene);
            stage.show();

            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudentDashboard(Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/librarysystem/view/student_dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Student Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
