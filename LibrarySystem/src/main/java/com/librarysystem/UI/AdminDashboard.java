package com.librarysystem.UI;

import com.librarysystem.model.BorrowRecord;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    private TableView<BorrowRecord> table;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Admin Dashboard");

        table = new TableView<>();
        table.setItems(getDummyData());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Kolom ID
        TableColumn<BorrowRecord, Integer> idCol = new TableColumn<>("ID");
        idCol.setMaxWidth(600);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Kolom Nama Peminjam
        TableColumn<BorrowRecord, String> borrowerCol = new TableColumn<>("Nama Peminjam");
        borrowerCol.setCellValueFactory(new PropertyValueFactory<>("borrower"));

        // Kolom Judul Buku
        TableColumn<BorrowRecord, String> titleCol = new TableColumn<>("Judul Buku");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Kolom Status dengan ComboBox
        TableColumn<BorrowRecord, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusCol.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>(
                    FXCollections.observableArrayList("Pending", "Dipinjam", "Dikembalikan")
            );
            {
                comboBox.setOnAction(e -> {
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
                    comboBox.setValue(status);
                    setGraphic(comboBox);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        // Tambahkan kolom ke tabel
        table.getColumns().addAll(idCol, borrowerCol, titleCol, statusCol);

        VBox root = new VBox(15, table);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Data dummy
    private ObservableList<BorrowRecord> getDummyData() {
        return FXCollections.observableArrayList(
                new BorrowRecord(1, "Andi", "Pemrograman Java", "Pending"),
                new BorrowRecord(2, "Budi", "Algoritma Dasar", "Dipinjam"),
                new BorrowRecord(3, "Citra", "Struktur Data", "Dikembalikan")
        );
    }

    public static void main(String[] args) {
        launch();
    }
}
