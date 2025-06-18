package com.librarysystem.util;

import com.librarysystem.controller.LoginController;
import com.librarysystem.model.User;

public class Session {
    public static User currentUser; // ✅ change from String to User
    public static LoginController.Role currentRole;
}
