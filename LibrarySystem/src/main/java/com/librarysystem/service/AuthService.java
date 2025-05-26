package com.librarysystem.service;

public class AuthService {
        public static boolean authenticate(String username, String password) {
            // Contoh validasi sementara
            return "admin".equals(username) && "123".equals(password);
    }

}
