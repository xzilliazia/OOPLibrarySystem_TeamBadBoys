package com.librarysystem.model;

import com.librarysystem.action.Authenticatable;

public abstract class User implements Authenticatable {
    private String id;
    private String name;
    private String username;
    private String password;

    public User(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
    @Override
    public void logout() {
    }
    public abstract String getRole();

}
