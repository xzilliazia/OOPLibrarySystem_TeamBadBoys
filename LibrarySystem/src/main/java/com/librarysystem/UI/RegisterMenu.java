package com.librarysystem.UI;

import com.librarysystem.controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterMenu {

    public void show(Stage stage) {
        Pane root = createRootPane();
        VBox leftPane = createLeftPane();
        Pane formBox = createFormBox(stage);
        root.getChildren().addAll(leftPane, formBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("UMM Library System - Register");
        stage.show();
    }

    private Pane createRootPane() {
        Image backgroundImg = new Image(getClass().getResource("/img/background.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1280, 800, false, false, false, false));
        Pane root = new Pane();
        root.setPrefSize(1280, 800);
        root.setBackground(new Background(background));
        return root;
    }

    private VBox createLeftPane() {
        ImageView logo = new ImageView(new Image(getClass().getResource("/img/ummlogo.png").toExternalForm()));
        logo.setFitWidth(245.05);
        logo.setFitHeight(241.64);
        logo.setPreserveRatio(true);

        Label welcomeLabel = new Label("WELCOME TO\nUMM LIBRARY");
        welcomeLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setTextAlignment(TextAlignment.CENTER);
        welcomeLabel.setWrapText(true);
        welcomeLabel.setMaxWidth(300);
        welcomeLabel.setAlignment(Pos.CENTER);

        VBox content = new VBox(20, logo, welcomeLabel);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(200, 0, 0, 0));

        StackPane stack = new StackPane(content);
        stack.setPadding(new Insets(0, 0, 0, 100));
        stack.setPrefWidth(600);
        stack.setAlignment(Pos.TOP_LEFT);

        return new VBox(stack);
    }

    private Pane createFormBox(Stage stage) {
        Pane formBox = new Pane();
        formBox.setLayoutX(700);
        formBox.setLayoutY(150);
        formBox.setPrefSize(533, 499.38);
        formBox.setStyle("-fx-background-color: rgba(255,255,255,0.75); -fx-background-radius: 20;");

        Label titleLabel = new Label("REGISTER");
        titleLabel.setFont(Font.font("Impact", 36));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.setLayoutX(66.5);
        titleLabel.setLayoutY(30);
        titleLabel.setPrefSize(400, 49.94);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        TextField usernameField = createTextField("Masukkan Username", 50, 100);
        TextField passwordField = createTextField("Masukkan Password", 50, 170);
        TextField prodiField = createTextField("Prodi", 50, 240);

        Button registerButton = createButton("REGISTER", 230, 53.93, "yellow", "yellow");
        registerButton.setLayoutX(151.5);
        registerButton.setLayoutY(320);
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String prodi = prodiField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || prodi.isEmpty()) {
                showAlert("Data Tidak Lengkap", "Mohon isi semua kolom.");
                return;
            }

            boolean success = new RegisterController().registerStudent(username,password, prodi);

            if (success) {
                showSuccessPopup();
                new LoginMenu().show(stage);  // Go back to login
            } else {
                showAlert("Gagal", "Terjadi kesalahan saat menyimpan data.");
            }
        });

        Button backBtn = new Button("←");
        backBtn.setStyle("-fx-background-radius: 50%; -fx-font-size: 30pt; -fx-background-color: transparent;");
        backBtn.setOnAction(e -> {
            stage.close();
            new LoginMenu().show(stage);
        });


        formBox.getChildren().addAll(titleLabel, usernameField, passwordField, prodiField, registerButton, backBtn);

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String major = prodiField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || major.isEmpty()) {
                showAlert("Data Tidak Lengkap", "Mohon isi semua kolom.");
                return;
            }

            boolean success = new RegisterController().registerStudent(username, password, major);

            if (success) {
                showSuccessPopup();
                new LoginMenu().show(stage);  // Go back to login after success
            } else {
                showAlert("Gagal", "Terjadi kesalahan saat menyimpan data.");
            }
        });


        return formBox;
    }

    private TextField createTextField(String prompt, double x, double y) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setLayoutX(x);
        tf.setLayoutY(y);
        tf.setPrefSize(421, 53.93);
        tf.setFont(Font.font("Tahoma", 15));
        tf.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: lightgray; -fx-padding: 10; -fx-background-color: white;");
        return tf;
    }

    private Button createButton(String text, double width, double height, String bgColor, String borderColor) {
        Button btn = new Button(text);
        btn.setPrefSize(width, height);
        btn.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: " + borderColor + ";" +
                        "-fx-padding: 10;" +
                        "-fx-background-color: " + bgColor + ";"
        );
        return btn;
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessPopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Berhasil");

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #888888;");
        box.setPadding(new Insets(30));

        Label label = new Label("register berhasil");
        label.setFont(Font.font("Arial", 20));
        label.setTextFill(Color.BLACK);

        Button btn = new Button("oke");
        btn.setStyle("-fx-background-color: orange; -fx-text-fill: black; -fx-font-size: 16px; -fx-background-radius: 20;");
        btn.setOnAction(e -> popup.close());

        box.getChildren().addAll(label, btn);

        Scene scene = new Scene(box, 300, 150);
        popup.setScene(scene);
        popup.showAndWait();
    }
}
