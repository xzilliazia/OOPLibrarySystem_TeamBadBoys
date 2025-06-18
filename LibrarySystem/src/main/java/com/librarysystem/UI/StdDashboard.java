package com.librarysystem.UI;

import com.librarysystem.util.Session;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StdDashboard extends Application {
    Label name = new Label(Session.currentUser);

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/img/background.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bg));

        // Logo
        ImageView logo = new ImageView(new Image(getClass().getResource("/img/ummlogo.png").toExternalForm()));
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        Label welcome = new Label("WELCOME TO\nUMM LIBRARY");
        applyFont(welcome, "28pt", true);

        Label name = new Label(Session.currentUser);
        name.setStyle("-fx-font-size: 16pt; -fx-text-fill: white;");

        Button btnPinjam = new Button("PINJAM");
        applyFont(btnPinjam, "30pt", true, "white");
        btnPinjam.setOnAction(e -> showBorrowMenu(stage));

        Button btnExit = new Button("KELUAR");
        btnExit.setStyle("-fx-background-color: yellow");
        applyFont(btnExit, "30pt", true, "yellow");
        btnExit.setOnAction(e -> stage.close());

        VBox centerBox = new VBox(20, logo, welcome, name, btnPinjam, btnExit);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(100));
        centerBox.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 20;");

        StackPane centerPane = new StackPane(centerBox);
        centerPane.setAlignment(Pos.CENTER);

        root.setCenter(centerPane);

        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("User Dashboard");
        stage.show();
    }

    private void showBorrowMenu(Stage stage) {
        // Ganti scene ke menu pinjam (StudentMenu, dll)
        StdDashboard menu = new StdDashboard();
        menu.start(stage); // Ganti scene saat ini
    }

    private void applyFont(Label label, String size, boolean bold) {
        String weight = bold ? "bold" : "normal";
        label.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size + "; -fx-font-weight: " + weight + "; -fx-text-fill: white;");
    }

    private void applyFont(Button button, String size, boolean bold, String color) {
        String weight = bold ? "bold" : "normal";
        button.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size + "; -fx-font-weight: " + weight +
                "; -fx-background-color: " + color + "; -fx-background-radius: 20; -fx-padding: 10 30;");
    }

}
