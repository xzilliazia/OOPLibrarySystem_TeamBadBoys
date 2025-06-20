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
        titleLabel.setLayoutY(80);
        titleLabel.setPrefSize(400, 49.94);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        TextField usernameField = createTextField("Masukkan Username", 50, 150);
        TextField passwordField = createTextField("Masukkan Password", 50, 220);
        TextField prodiField = createTextField("Prodi", 50, 290);

        Button registerButton = createRegisterButton("REGISTER", 230, 53.93);
        registerButton.setLayoutX(151.5);
        registerButton.setLayoutY(400);
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
        backBtn.setStyle(
                "-fx-background-radius: 50%;" +
                        "-fx-min-width: 50px;" +
                        "-fx-min-height: 50px;" +
                        "-fx-font-size: 30pt;" +
                        "-fx-text-fill: #171616;" + // Warna panah abu-abu saat normal
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-border-width: 2px;"
        );

// Efek hover - hanya panah yang berubah
        backBtn.setOnMouseEntered(e -> {
            backBtn.setStyle(
                    "-fx-background-radius: 50%;" +
                            "-fx-min-width: 50px;" +
                            "-fx-min-height: 50px;" +
                            "-fx-font-size: 30pt;" +
                            "-fx-text-fill: #f7ff00;" + // Warna panah oranye menyala saat hover
                            "-fx-background-color: transparent;" +
                            "-fx-border-color: transparent;" + // Border tetap transparan
                            "-fx-border-radius: 50%;" +
                            "-fx-border-width: 2px;" +
                            "-fx-font-weight: bold;"
            );
        });

// Kembali ke normal saat mouse keluar
        backBtn.setOnMouseExited(e -> {
            backBtn.setStyle(
                    "-fx-background-radius: 50%;" +
                            "-fx-min-width: 50px;" +
                            "-fx-min-height: 50px;" +
                            "-fx-font-size: 30pt;" +
                            "-fx-text-fill: #a0a0a0;" +
                            "-fx-background-color: transparent;" +
                            "-fx-border-color: transparent;" +
                            "-fx-border-radius: 50%;" +
                            "-fx-border-width: 2px;"
            );
        });

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
