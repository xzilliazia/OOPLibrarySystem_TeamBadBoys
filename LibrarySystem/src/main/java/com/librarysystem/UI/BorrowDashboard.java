package com.librarysystem.UI;

import com.librarysystem.model.Book;
import com.librarysystem.model.BorrowStatus;
import com.librarysystem.model.User;
import com.librarysystem.service.BookUtil;
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

public class BorrowDashboard extends Application {

    private TableView<PropertyBook> tableView;
    private ObservableList<PropertyBook> data;
    private User currentUser;
    private Stage previousStage;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Set background
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/img/background2.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bgImage));

        // LEFT PANEL
        VBox leftPanel = createSearchPanel();
        leftPanel.setPrefWidth(400);
        leftPanel.setAlignment(Pos.CENTER); // isi di tengah

        // Tambahkan margin ke kiri agar tidak nempel ke table
        BorderPane.setMargin(leftPanel, new Insets(30, 20, 30, 30)); // top, right, bottom, left
        root.setLeft(leftPanel);

        // TABLEVIEW
        tableView = createTableView();
        loadData();

        VBox tableBox = new VBox();
        tableBox.setPadding(new Insets(30, 50, 30, 30)); // jarak semua sisi
        tableBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(tableView, Priority.ALWAYS); // agar tableView isi seluruh tinggi
        tableBox.getChildren().add(tableView);

        root.setCenter(tableBox);

        Scene scene = new Scene(root, 1280, 800);
        stage.setTitle("Student Book Search");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createSearchPanel() {
            // Panel isi tombol dan input
            VBox contentBox = new VBox(15);
            contentBox.setAlignment(Pos.CENTER);

            Button backBtn = new Button("←");
            backBtn.setStyle("-fx-background-radius: 50%; -fx-font-size: 16pt; -fx-background-color: lightgray;");
            backBtn.setOnAction(e -> {
                if (previousStage != null) {
                    // Close the current stage (BorrowDashboard)
                    Stage currentStage = (Stage) backBtn.getScene().getWindow();
                    currentStage.close();

                    // Reopen the previous StdDashboard stage
                    StdDashboard stdDashboard = new StdDashboard();
                    stdDashboard.setCurrentUser(currentUser);
                    stdDashboard.start(previousStage);
                }
            });

            Label title = new Label("CARI BUKU");
            title.setStyle("-fx-font-size: 24pt; -fx-font-weight: bold;");

            TextField searchField = new TextField();
            searchField.setPromptText("Masukkan nama buku/Author");
            searchField.setStyle("-fx-background-radius: 25; -fx-padding: 10; -fx-font-size: 12pt;");

            Button searchBtn = new Button("cari");
            searchBtn.setStyle("-fx-background-radius: 25; -fx-background-color: orange; -fx-font-weight: bold; -fx-font-size: 14pt;");
            searchBtn.setPrefWidth(200);
            searchBtn.setPrefHeight(40);
            searchBtn.setOnAction(e -> searchBooks(searchField.getText()));

            Button pinjamBtn = new Button("pinjam");
            pinjamBtn.setStyle("-fx-background-radius: 25; -fx-background-color: white; -fx-font-weight: bold; -fx-font-size: 14pt;");
            pinjamBtn.setPrefWidth(200);
            pinjamBtn.setPrefHeight(40);
            pinjamBtn.setOnAction(e -> handlePinjam());

            contentBox.getChildren().addAll(backBtn, title, searchField, searchBtn, pinjamBtn);

            // Label catatan di bawah
            Label catatan = new Label("*untuk pengembalian buku langsung ke petugas perpustakaan");
            catatan.setStyle("-fx-font-size: 10pt;");
            catatan.setWrapText(true);

            // Bungkus semuanya dalam BorderPane
            BorderPane panel = new BorderPane();
            panel.setPadding(new Insets(60));
            panel.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 25;");
            panel.setCenter(contentBox);
            panel.setBottom(catatan);
            BorderPane.setMargin(catatan, new Insets(20, 0, 0, 0)); // jarak atas dari catatan

            return new VBox(panel);
        }


    private TableView<PropertyBook> createTableView() {
        TableView<PropertyBook> table = new TableView<>();
        table.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-text-fill: white;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<PropertyBook, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PropertyBook, String> titleCol = new TableColumn<>("NAMA BUKU");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<PropertyBook, String> authorCol = new TableColumn<>("AUTHOR");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<PropertyBook, String> categoryCol = new TableColumn<>("KATEGORI");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<PropertyBook, Integer> stockCol = new TableColumn<>("STOK");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(idCol, titleCol, authorCol, categoryCol, stockCol);
        return table;
    }

    private void loadData() {
        ArrayList<Book> books = BookUtil.loadBooks();
        ArrayList<PropertyBook> props = PropertyBook.bookToBind(books);
        data = FXCollections.observableArrayList(props);
        tableView.setItems(data);
    }

    private void searchBooks(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            loadData();
        } else {
            ArrayList<Book> found = BookUtil.loadBooks(); // Load all books first

            ArrayList<Book> filtered = new ArrayList<>();
            for (Book book : found) {
                if (String.valueOf(book.getBookId()).contains(keyword)
                        || book.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(keyword.toLowerCase())
                        || book.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                    filtered.add(book);
                }
            }

            ArrayList<PropertyBook> props = PropertyBook.bookToBind(filtered);
            tableView.setItems(FXCollections.observableArrayList(props));
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();

        // Build your UI (same as in start())
        // ...
        // Reuse your existing code, e.g. createSearchPanel(), createTableView(), etc.

        // Example:
        VBox leftPanel = createSearchPanel();
        root.setLeft(leftPanel);

        tableView = createTableView();
        loadData();

        VBox tableBox = new VBox(tableView);
        root.setCenter(tableBox);

        return new Scene(root, 1280, 800);
    }

    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    private void handlePinjam() {
        PropertyBook selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Pinjam Buku", "Silakan pilih buku yang ingin dipinjam.");
            return;
        }

        if (selected.getStock() <= 0) {
            showAlert("Pinjam Buku", "Stok buku habis. Tidak dapat dipinjam.");
            return;
        }

        selected.setStock(selected.getStock() - 1);
        BookUtil.updateBookStock(selected.getId(), selected.getStock());

        // NEW: Insert borrow record!
        BookUtil.insertBorrowRecord(currentUser.getUserId(), selected.getId(), BorrowStatus.PENDING);

        tableView.refresh();

        showAlert("Pinjam Buku", "Berhasil meminjam buku: " + selected.getTitle());
    }

}


