package com.librarysystem.UI;

import com.librarysystem.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LoginMenu {

    public void show(Stage stage) {
        Pane root = createRootPane();
        VBox leftPane = createLeftPane();
        Pane formBox = createFormBox(stage);
        root.getChildren().addAll(leftPane, formBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Umm Library System - Login");
        stage.show();
    }

    private Pane createRootPane() {
        Image backgroundImg = new Image(getClass().getResource("/img/for login and register panel.jpg").toExternalForm());
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
        welcomeLabel.setFont(Font.font("Tahoma", FontWeight.BOLD,40));
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

        Label titleLabel = new Label("LOG IN HERE");
        titleLabel.setFont(Font.font("Impact", 36));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.setLayoutX(66.5);
        titleLabel.setLayoutY(30);
        titleLabel.setPrefSize(400, 49.94);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        TextField usernameField = createTextField("Masukkan Username", 50, 140);
        PasswordField passwordField = (PasswordField) createTextField("Masukkan Password/ID", 50, 210);

        Label errorLabel = new Label("Username atau password salah!");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);
        errorLabel.setLayoutX(50);
        errorLabel.setLayoutY(285);
        errorLabel.setPrefWidth(400);
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setTextAlignment(TextAlignment.CENTER);

        Button loginButton = createLoginButton("MASUK", 230, 53.93);
        Button registerButton = createRegisterButton("REGISTER", 230, 53.93);

        VBox buttonBox = new VBox(10, loginButton, registerButton);
        buttonBox.setLayoutX(151.5);
        buttonBox.setLayoutY(320);
        buttonBox.setAlignment(Pos.CENTER);

        formBox.getChildren().addAll(titleLabel, usernameField, passwordField, errorLabel, buttonBox);

        // Event login
        loginButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                errorLabel.setText("Mohon isi semua kolom.");
                errorLabel.setVisible(true);
                return;
            }

            new LoginController().login(user, pass, stage);
        });

        registerButton.setOnAction(e -> new RegisterMenu().show(stage));

        return formBox;
    }

    private TextField createTextField(String prompt, double x, double y) {
        TextField tf = prompt.toLowerCase().contains("password") ? new PasswordField() : new TextField();
        tf.setPromptText(prompt);
        tf.setLayoutX(x);
        tf.setLayoutY(y);
        tf.setPrefSize(421, 53.93);
        tf.setFont(Font.font("Tahoma", 15));

        String normalStyle = "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: lightgray;" +
                "-fx-border-width: 2;" +
                "-fx-padding: 10;" +
                "-fx-background-color: white;";

        String hoverStyle = "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: #a0a0a0;" + // Warna border saat hover
                "-fx-border-width: 2;" +
                "-fx-padding: 10;" +
                "-fx-background-color: white;";

        String focusedStyle = "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: #f3d95f;" + // Warna border saat focus (biru)
                "-fx-border-width: 2;" +
                "-fx-padding: 10;" +
                "-fx-background-color: white;";

        tf.setStyle(normalStyle);

        tf.setOnMouseEntered(e -> {
            if (!tf.isFocused()) {
                tf.setStyle(hoverStyle);
            }
        });

        tf.setOnMouseExited(e -> {
            if (!tf.isFocused()) {
                tf.setStyle(normalStyle);
            }
        });

        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tf.setStyle(focusedStyle); // Saat focus (klik/select)
            } else {
                tf.setStyle(normalStyle); // Saat kehilangan focus
            }
        });

        return tf;
    }

    private Button createLoginButton(String text, double width, double height) {
        Button btn = new Button(text);
        btn.setPrefSize(width, height);

        String normalStyle = "-fx-font-weight: bold;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2;" +  // Tambahkan ketebalan border
                "-fx-padding: 10;" +
                "-fx-background-color: white;" +
                "-fx-text-fill: black;";

        String hoverStyle = "-fx-font-weight: bold;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: #a0a0a0;" +  // Hanya ubah border color
                "-fx-border-width: 2;" +
                "-fx-padding: 10;" +
                "-fx-background-color: white;" +  // Background tetap sama
                "-fx-text-fill: black;";

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

        return btn;
    }

    private Button createRegisterButton(String text, double width, double height) {
        Button btn = new Button(text);
        btn.setPrefSize(width, height);

        String normalStyle = "-fx-font-weight: bold;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: yellow;" +
                "-fx-border-width: 2;" +  // Tambahkan ketebalan border
                "-fx-padding: 10;" +
                "-fx-background-color: yellow;" +
                "-fx-text-fill: black;";

        String hoverStyle = "-fx-font-weight: bold;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: #ffcc00;" +  // Hanya ubah border color
                "-fx-border-width: 2;" +
                "-fx-padding: 10;" +
                "-fx-background-color: yellow;" +  // Background tetap sama
                "-fx-text-fill: black;";

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

        return btn;
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}