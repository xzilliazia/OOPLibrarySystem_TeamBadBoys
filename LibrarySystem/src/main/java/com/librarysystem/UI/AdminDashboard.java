package com.librarysystem.UI;

import com.librarysystem.model.Admin;
import com.librarysystem.model.BorrowRecord;
import com.librarysystem.model.BorrowStatus;
import com.librarysystem.model.User;
import com.librarysystem.util.DatabaseConnection;
import com.librarysystem.util.Session;
import com.librarysystem.service.BookUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class AdminDashboard {

    private TableView<BorrowRecord> table;
    private User currentUser;
    private Stage userStage;

    public void start(Stage stage) {
        this.currentUser = Session.currentUser;
        Scene scene = createScene(stage);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    public Scene createScene(Stage stage) {
        BorderPane root = new BorderPane();

        // ✅ Background image
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/img/for admin.jpg").toExternalForm(), 1280, 800, false, true),
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

        Label userLabel = new Label("Selamat Datang, \n" + currentUser.getUsername());
        applyFont(userLabel, "14pt", true);
        userLabel.setMaxWidth(Double.MAX_VALUE);
        userLabel.setAlignment(Pos.CENTER);

        Button btnManageBuku = createHoverBorderButton("Kelola Buku", "orange", "orange", "#ff8000");
        btnManageBuku.setOnAction(e -> {
            stage.close();
            new BookManager().start(stage);
        });

        Button btnHapusUser = createHoverBorderButton("Hapus User", "orange", "orange", "#ff8000");
        btnHapusUser.setOnAction(e -> {
            if (userStage != null && userStage.isShowing()) {
                userStage.toFront();
                return;
            }

            TableView<User> userTable = new TableView<>();
            userTable.setItems(getAllStudents());
            userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            userTable.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

            TableColumn<User, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            idCol.setStyle("-fx-alignment: CENTER;");

            TableColumn<User, String> nameCol = new TableColumn<>("Username");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            nameCol.setStyle("-fx-alignment: CENTER-LEFT;");

            TableColumn<User, Void> actionCol = new TableColumn<>("Aksi");
            actionCol.setCellFactory(col -> new TableCell<>() {
                private final Button deleteBtn = createDeleteButton();

                {
                    deleteBtn.setOnAction(e2 -> {
                        User selectedUser = getTableView().getItems().get(getIndex());
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                                "Yakin ingin menghapus user ini?", ButtonType.YES, ButtonType.NO);
                        confirm.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                if (deleteUserById(selectedUser.getUserId())) {
                                    getTableView().getItems().remove(selectedUser);
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Gagal menghapus user.").show();
                                }
                            }
                        });
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : deleteBtn);
                    setAlignment(Pos.CENTER);
                }
            });
            idCol.setPrefWidth(80);
            nameCol.setPrefWidth(300);
            actionCol.setPrefWidth(120);

            userTable.getColumns().addAll(idCol, nameCol, actionCol);

            VBox layout = new VBox(20, userTable);
            layout.setPadding(new Insets(30));
            layout.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

            Scene userScene = new Scene(layout, 600, 400);
            userStage = new Stage(); // Simpan ke field
            userStage.setTitle("Kelola User (Student)");
            userStage.setResizable(false);
            userStage.setScene(userScene);

            // Reset field saat ditutup
            userStage.setOnCloseRequest(evt -> userStage = null);

            userStage.show();
        });

        Button btnExit = createHoverBorderButton("KELUAR", "white", "white", "#a0a0a0");
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


        sidebar.getChildren().addAll(userLabel, btnManageBuku, btnHapusUser, btnExit);
        VBox leftContainer = new VBox(sidebar);
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setPadding(new Insets(0, 0, 0, 30));
        leftContainer.setPrefWidth(400);
        root.setLeft(leftContainer);

        // ✅ Table setup
        table = new TableView<>();
        ObservableList<BorrowRecord> allRecords = FXCollections.observableArrayList(BookUtil.getAllBorrowRecords());
        allRecords.sort((a, b) -> a.getBorrower().compareToIgnoreCase(b.getBorrower()));

        String lastBorrower = "";
        for (BorrowRecord record : allRecords) {
            if (record.getBorrower().equals(lastBorrower)) {
                record.setBorrower("");
            } else {
                lastBorrower = record.getBorrower();
            }
        }
        table.setItems(allRecords);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BorrowRecord, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BorrowRecord, String> borrowerCol = new TableColumn<>("Nama Peminjam");
        borrowerCol.setCellValueFactory(new PropertyValueFactory<>("borrower"));

        TableColumn<BorrowRecord, String> titleCol = new TableColumn<>("Judul Buku");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BorrowRecord, BorrowStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setCellFactory(column -> createStatusCell());

        table.getColumns().addAll(idCol, borrowerCol, titleCol, statusCol);


        VBox tableBox = new VBox(table);
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(30));
        root.setCenter(tableBox);

        return new Scene(root, 1280, 800);
    }

    private TableCell<BorrowRecord, BorrowStatus> createStatusCell() {
        return new TableCell<>() {
            private final ComboBox<BorrowStatus> comboBox = new ComboBox<>(
                    FXCollections.observableArrayList(BorrowStatus.values())
            );
            private boolean initialized = false;

            {
                comboBox.setOnAction(e -> {
                    if (!initialized) return;

                    BorrowRecord record = getTableView().getItems().get(getIndex());
                    BorrowStatus selectedStatus = comboBox.getValue();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Yakin ingin mengganti status?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            record.setStatus(selectedStatus);

                            // TODO: Update database
                            BookUtil.updateBorrowStatus(record.getId(), selectedStatus);

                        } else {
                            comboBox.setValue(record.getStatus());
                        }
                    });
                });

                // Display user-friendly text
                comboBox.setCellFactory(cb -> new ListCell<>() {
                    @Override
                    protected void updateItem(BorrowStatus item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getDisplayName());
                    }
                });
                comboBox.setButtonCell(comboBox.getCellFactory().call(null));
            }

            @Override
            protected void updateItem(BorrowStatus status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    initialized = false;
                    comboBox.setValue(status);
                    setGraphic(comboBox);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: CENTER;");
                    initialized = true;
                }
            }
        };
    }

    private boolean deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND role = 'student'";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    private ObservableList<User> getAllStudents() {
        ObservableList<User> students = FXCollections.observableArrayList();
        String sql = "SELECT id, username, role FROM users WHERE role = 'student'";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String role = rs.getString("role");

                students.add(new User(id, username, role) {});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    private Button createHoverBorderButton(String text, String bgColor, String borderColor, String hoverBorderColor) {
        Button btn = new Button(text);

        // Normal style
        String normalStyle = "-fx-font-family: 'Poppins';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14pt;" +
                "-fx-background-color: " + bgColor + ";" +
                "-fx-text-fill: black;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-padding: 10 30;";

        // Hover style (only border changes)
        String hoverStyle = "-fx-font-family: 'Poppins';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14pt;" +
                "-fx-background-color: " + bgColor + ";" +
                "-fx-text-fill: black;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: " + hoverBorderColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-padding: 10 30;";

        btn.setStyle(normalStyle);

        // Hover effects
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

        return btn;
    }

    private Button createDeleteButton() {
        Button btn = new Button("Hapus");

        // Normal style
        String normalStyle = "-fx-background-color: #f44336;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 5 15;" +
                "-fx-border-color: #f44336;" +
                "-fx-border-radius: 10;" +
                "-fx-border-width: 2;";

        // Hover style (only border changes)
        String hoverStyle = "-fx-background-color: #f44336;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 5 15;" +
                "-fx-border-color: #8e0a0a;" + // Darker red border
                "-fx-border-radius: 10;" +
                "-fx-border-width: 2;";

        btn.setStyle(normalStyle);

        // Hover effects
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

        return btn;
    }

    private void applyFont(Label label, String size, boolean bold) {
        String weight = bold ? "bold" : "normal";
        label.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size +
                "; -fx-font-weight: " + weight + "; -fx-text-fill: black;");
    }
}