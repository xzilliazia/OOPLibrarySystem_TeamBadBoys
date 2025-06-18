package com.librarysystem.UI;

import com.librarysystem.model.BorrowRecord;
import com.librarysystem.model.BorrowStatus;
import com.librarysystem.model.User;
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

public class AdminDashboard {

    private TableView<BorrowRecord> table;
    private User currentUser;

    public void start(Stage stage) {
        this.currentUser = Session.currentUser;
        Scene scene = createScene(stage);

        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    public Scene createScene(Stage stage) {
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

        Label userLabel = new Label(currentUser.getUsername());
        applyFont(userLabel, "18pt", true);
        userLabel.setMaxWidth(Double.MAX_VALUE);
        userLabel.setAlignment(Pos.CENTER);

        Button btnManageBuku = new Button("Manage User");
        applyFont(btnManageBuku, "14pt", true, "#4CAF50", "white");
        btnManageBuku.setOnAction(e -> {
            stage.close();
            new BookManager().start(stage);
        });

        Button btnHapusUser = new Button("Hapus User");
        applyFont(btnHapusUser, "14pt", true, "#f44336", "white");

        Button btnExit = new Button("KELUAR");
        applyFont(btnExit, "14pt", true, "orange", "black");
        btnExit.setOnAction(e -> {
            stage.close();  // close the current dashboard
            new LoginMenu().show(new Stage());
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

    private void applyFont(Label label, String size, boolean bold) {
        String weight = bold ? "bold" : "normal";
        label.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size +
                "; -fx-font-weight: " + weight + "; -fx-text-fill: black;");
    }

    private void applyFont(Button button, String size, boolean bold, String bgColor, String textColor) {
        String weight = bold ? "bold" : "normal";
        button.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size +
                "; -fx-font-weight: " + weight + "; -fx-background-color: " + bgColor +
                "; -fx-text-fill: " + textColor + "; -fx-background-radius: 20; -fx-padding: 10 30;");
    }
}
