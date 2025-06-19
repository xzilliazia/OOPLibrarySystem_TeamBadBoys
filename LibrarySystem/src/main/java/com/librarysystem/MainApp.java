package com.librarysystem;

import com.librarysystem.UI.LoginMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        new LoginMenu().show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}