package com.librarysystem.UI;

import com.librarysystem.model.Book;
import com.librarysystem.model.Student;
import com.librarysystem.model.User;
import com.librarysystem.model.BorrowRecord;
import com.librarysystem.service.BookUtil;
import com.librarysystem.service.UserUtil;
import com.librarysystem.util.Session;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StdDashboard extends Application {
    private TableView<BorrowRecord> tableView;
    private ObservableList<BorrowRecord> data;
    private Label userLabel;
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        this.currentUser = Session.currentUser;

        // Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/img/background.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bg));

        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;");

        userLabel = new Label(Session.currentUser.getUsername());
        applyFont(userLabel, "18pt", true);
        userLabel.setMaxWidth(Double.MAX_VALUE);
        userLabel.setAlignment(Pos.CENTER);

        Button btnPinjam = new Button("PINJAM");
        applyFont(btnPinjam, "14pt", true, "white", "black");
        btnPinjam.setOnAction(e -> {
            BorrowDashboard borrow = new BorrowDashboard();
            borrow.setCurrentUser(currentUser);
            borrow.setPreviousStage(stage);
            borrow.start(new Stage());
            stage.close();
        });

        Button btnEditProfile = new Button("EDIT PROFILE");
        applyFont(btnEditProfile, "14pt", true, "orange", "black");
        btnEditProfile.setOnAction(e -> handleEditProfile());

        Button btnExit = new Button("KELUAR");
        applyFont(btnExit, "14pt", true, "orange", "black");
        btnExit.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Keluar");
            alert.setHeaderText("Apakah Anda ingin keluar?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    new LoginMenu().show(stage);
                }
                // Jika "NO", tidak melakukan apa-apa (tetap di halaman sekarang)
            });
        });


        sidebar.getChildren().addAll(userLabel, btnPinjam, btnEditProfile, btnExit);

        // Wrapping sidebar in a VBox container to enable vertical centering on left region
        VBox leftContainer = new VBox();
        leftContainer.setAlignment(Pos.CENTER); // Vertical and horizontal center
        leftContainer.getChildren().add(sidebar);
        leftContainer.setPadding(new Insets(0, 0, 0, 30));
        leftContainer.setPrefWidth(400);
        leftContainer.setMaxWidth(400);
        leftContainer.setMinWidth(400);
        // Make leftContainer take full height of the scene so sidebar can center vertically within it
        VBox.setVgrow(leftContainer, Priority.ALWAYS);
        leftContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        root.setLeft(leftContainer);

        // Table
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BorrowRecord, Void> colNumber = new TableColumn<>("No.");
        colNumber.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        colNumber.setPrefWidth(50);

        TableColumn<BorrowRecord, String> colTitle = new TableColumn<>("Judul");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BorrowRecord, String> colDate = new TableColumn<>("Tanggal Pinjam");
        colDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        TableColumn<BorrowRecord, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        tableView.getColumns().addAll(colNumber, colTitle, colDate, colStatus);

        VBox tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(30, 30, 30, 30));
        tableBox.setFillWidth(true);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        tableView.setMaxHeight(Double.MAX_VALUE);
        tableBox.getChildren().addAll(tableView);

        root.setCenter(tableBox);

        refreshTable();

        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("User Dashboard");
        stage.show();
    }

    private void refreshTable() {
        // Make sure BookUtil provides this method properly
        List<BorrowRecord> records = BookUtil.getBorrowsForUser(currentUser.getUserId());
        data = FXCollections.observableArrayList(records);
        tableView.setItems(data);
    }

    private void applyFont(Label label, String size, boolean bold) {
        String weight = bold ? "bold" : "normal";
        label.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size + "; -fx-font-weight: " + weight + "; -fx-text-fill: black;");
    }

    private void applyFont(Button button, String size, boolean bold, String bgColor, String textColor) {
        String weight = bold ? "bold" : "normal";
        button.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size + "; -fx-font-weight: " + weight +
                "; -fx-background-color: " + bgColor + "; -fx-text-fill: " + textColor + "; -fx-background-radius: 20; -fx-padding: 10 30;");
    }

    private TextField createStyledTextField() {
        TextField tf = new TextField();
        tf.setStyle(
                "-fx-background-color: #f9f9f9;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 8 12;"
        );
        return tf;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-font-weight: bold; " +
                "-fx-background-radius: 20; -fx-border-radius: 20; " +
                "-fx-border-color: " + color + "; -fx-padding: 10 20 10 20; " +
                "-fx-background-color: " + color + "; -fx-text-fill: white;");
        return btn;
    }

    private void loadData() {
        refreshTable();
    }

    private void handleEditProfile() {
        User userToEdit = currentUser;
        Stage popup = new Stage();
        popup.setTitle("Edit Profile");

        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        TextField usernameField = createStyledTextField();
        usernameField.setText(userToEdit.getUsername());

        TextField majorField = createStyledTextField();
        majorField.setText(userToEdit.getMajor());


        Button submit = createStyledButton("Simpan Perubahan", "#FFA500");
        submit.setOnAction(e -> {
            try {
                String usernameText = usernameField.getText();
                String majorText = majorField.getText();

                String newUsername = (usernameText == null || usernameText.isEmpty()) ? currentUser.getUsername() : usernameText;
                String newMajor = (majorText == null || majorText.isEmpty()) ? currentUser.getMajor() : majorText;

                currentUser.setUsername(newUsername);
                currentUser.setMajor(newMajor);

                UserUtil.updateUser(currentUser);

                refreshTable();  // or whatever your table reload method is
                userLabel.setText(currentUser.getUsername()); // update label

                showAlert("Sukses", "Data profil berhasil diperbarui.");
                popup.close();
            } catch (Exception ex) {
                showAlert("Error", "Terjadi kesalahan saat memperbarui profil.\n" + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // 🚀 Add fields + button to form!
        form.getChildren().addAll(new Label("Username:"), usernameField,
                new Label("Major:"), majorField,
                submit);

        Scene scene = new Scene(form, 400, 300);
        popup.setScene(scene);
        popup.initOwner(tableView.getScene().getWindow());
        popup.show();
    }
}