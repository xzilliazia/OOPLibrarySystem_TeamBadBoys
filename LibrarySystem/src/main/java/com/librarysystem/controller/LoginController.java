package com.librarysystem.controller;

import com.librarysystem.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;



public class LoginController {

    public enum Role {
        MAHASISWA, ADMIN, INVALID
    }

    public Role login(String usernameOrNim, String password){
        if(isAdmin(usernameOrNim, password)){
            return Role.ADMIN;
        } else if (isMahasiswa(usernameOrNim, password)) {
            return Role.MAHASISWA;
        } else {
            return Role.INVALID;
        }
    }

    private boolean isAdmin(String username, String password){
        String query = "SELECT * FROM admin WHERE username=? AND password=?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isMahasiswa(String nim, String password){
        String query = "SELECT * FROM mahasiswa WHERE nim = ? AND password = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, nim);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}