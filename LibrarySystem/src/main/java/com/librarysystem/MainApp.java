package com.librarysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/librarysystem/fxml/hello-view.fxml"));
        Parent root = fxmlLoader.load(); // Load the FXML
        Scene scene = new Scene(root, 800, 600); // Create the scene with the loaded root
        primaryStage.setTitle("Library System");
        primaryStage.setScene(scene); // Set the scene to the stage
        primaryStage.show(); // Show the window
    }

    public static void main(String[] args) {
        launch();
    }
}