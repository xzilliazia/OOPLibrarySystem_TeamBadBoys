package com.librarysystem.action;

public interface Authenticatable {
    boolean login(String username, String password);
    void logout();
}
