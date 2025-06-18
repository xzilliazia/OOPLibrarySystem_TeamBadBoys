package com.librarysystem.UI;

import com.librarysystem.model.Admin;
import com.librarysystem.model.BorrowRecord;
import com.librarysystem.model.User;
import com.librarysystem.util.Session;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    private TableView<BorrowRecord> table;
    private User currentUser;

    @Override
    public void start(Stage stage) {
        this.currentUser = Session.currentUser;

        BorderPane root = new BorderPane();

        // ✅ Background image
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/img/background.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bg));

        // ✅ Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;");

        Label userLabel = new Label(currentUser.getUsername()); // tampilkan nama
        applyFont(userLabel, "18pt", true);
        userLabel.setMaxWidth(Double.MAX_VALUE);
        userLabel.setAlignment(Pos.CENTER);

        Button btnTambahBuku = new Button("Tambah Buku");
        applyFont(btnTambahBuku, "14pt", true, "#4CAF50", "white");

        Button btnHapusUser = new Button("Hapus User");
        applyFont(btnHapusUser, "14pt", true, "#f44336", "white");

        Button btnExit = new Button("KELUAR");
        applyFont(btnExit, "14pt", true, "orange", "black");
        btnExit.setOnAction(e -> new LoginMenu().show(stage));

        sidebar.getChildren().addAll(userLabel, btnTambahBuku, btnHapusUser, btnExit);

        VBox leftContainer = new VBox();
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.getChildren().add(sidebar);
        leftContainer.setPadding(new Insets(0, 0, 0, 30));
        leftContainer.setPrefWidth(400);
        VBox.setVgrow(leftContainer, Priority.ALWAYS);
        root.setLeft(leftContainer);

        // ✅ Table
        table = new TableView<>();
        table.setItems(getDummyData());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BorrowRecord, Integer> idCol = new TableColumn<>("ID");
        idCol.setMaxWidth(600);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BorrowRecord, String> borrowerCol = new TableColumn<>("Nama Peminjam");
        borrowerCol.setCellValueFactory(new PropertyValueFactory<>("borrower"));

        TableColumn<BorrowRecord, String> titleCol = new TableColumn<>("Judul Buku");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BorrowRecord, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusCol.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>(
                    FXCollections.observableArrayList("Pending", "Dipinjam", "Dikembalikan")
            );
            private boolean initialized = false;

            {
                comboBox.setOnAction(e -> {
                    if (!initialized) return; // lewati jika belum diinisialisasi
                    BorrowRecord record = getTableView().getItems().get(getIndex());
                    String selectedStatus = comboBox.getValue();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Yakin ingin mengganti status?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            record.setStatus(selectedStatus);
                            // TODO: Update database di sini nanti
                        } else {
                            comboBox.setValue(record.getStatus());
                        }
                    });
                });
            }

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    initialized = false; // cegah trigger saat setValue
                    comboBox.setValue(status);
                    setGraphic(comboBox);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: CENTER;");
                    initialized = true; // baru aktifkan setelah semua siap
                }
            }
        });

        table.getColumns().addAll(idCol, borrowerCol, titleCol, statusCol);

        VBox tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(30));
        tableBox.getChildren().add(table);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.setCenter(tableBox);

        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    private ObservableList<BorrowRecord> getDummyData() {
        return FXCollections.observableArrayList(
                new BorrowRecord(1, "Andi", "Pemrograman Java", "Pending"),
                new BorrowRecord(2, "Budi", "Algoritma Dasar", "Dipinjam"),
                new BorrowRecord(3, "Citra", "Struktur Data", "Dikembalikan")
        );
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

    public static void main(String[] args) {
        // Simulasi login
        Session.currentUser = new Admin(1,"admin 1");
        AdminDashboard.launch(args);
    }
}
