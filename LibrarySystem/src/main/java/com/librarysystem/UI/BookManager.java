package com.librarysystem.UI;

import com.librarysystem.model.Book;
import com.librarysystem.service.BookUtil;
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

public class BookManager {
    private TableView<PropertyBook> tableView;
    private ObservableList<PropertyBook> data;

    public void start(Stage stage) {
        Scene scene = createScene(stage);
        stage.setScene(scene);
        stage.setTitle("Book Manager");
        stage.show();
    }

    public Scene createScene(Stage stage) {
        // Root with background
        BorderPane root = new BorderPane();
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/img/background.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bgImage));

        // ───── TABLE ─────
        tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-background-color: rgba(255,255,255,0.85);");

        createTableColumns();
        loadData();

        VBox tableBox = new VBox(tableView);
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(30));
        root.setCenter(tableBox);

        // ───── CONTROL PANEL (LEFT) ─────
        VBox leftPanel = new VBox(20);
        leftPanel.setPadding(new Insets(30));
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPrefWidth(300);
        leftPanel.setMaxHeight(380);
        leftPanel.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;");

        TextField searchField = createStyledTextField("Nama Buku/ID Buku");
        searchField.setPrefWidth(200);
        Button searchBtn = createStyledButton("Cari", "#FFA500");
        searchBtn.setPrefWidth(200);
        searchBtn.setOnAction(e -> searchBooks(searchField.getText()));

        Button addBtn = createStyledButton("TAMBAH BUKU", "#FFA500");
        addBtn.setPrefWidth(200);
        addBtn.setOnAction(e -> openAddBookWindow());

        Button editBtn = createStyledButton("EDIT BUKU", "#FFA500");
        editBtn.setPrefWidth(200);
        editBtn.setOnAction(e -> openEditBookWindow());

        Button delBtn = createStyledButton("HAPUS BUKU", "#FFA500");
        delBtn.setPrefWidth(200);
        delBtn.setOnAction(e -> openDeleteBookWindow());

//

        Button backBtn = new Button("←");
        backBtn.setStyle("-fx-background-radius: 50%; -fx-font-size: 16pt; -fx-background-color: #d82e2e;");
        backBtn.setOnAction(e -> {
            stage.close();
            new AdminDashboard().start(stage);
        });

        leftPanel.getChildren().addAll(searchField, searchBtn, addBtn,editBtn, delBtn, backBtn);

        StackPane leftWrapper = new StackPane(leftPanel);
        leftWrapper.setPrefWidth(320);
        leftWrapper.setPadding(new Insets(0, 20, 0, 40));

        BorderPane mainContent = new BorderPane();
        mainContent.setLeft(leftWrapper);
        mainContent.setCenter(tableBox);
        mainContent.setPadding(new Insets(30));

        root.setCenter(mainContent);

        return new Scene(root, 1280, 800);
    }

    private void openAddBookWindow() {
        Stage popup = new Stage();
        popup.setTitle("Tambah Buku");

        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        TextField title = createStyledTextField("Nama Buku");
        TextField author = createStyledTextField("Author");
        TextField category = createStyledTextField("Kategori");
        TextField stock = createStyledTextField("Stock");

        HBox categoryStockBox = new HBox(10, category, stock);

        Button submit = createStyledButton("Tambah", "#FFA500");
        submit.setOnAction(e -> {
            addBook(title.getText(), author.getText(), category.getText(), stock.getText());
            popup.close();
        });

        form.getChildren().addAll(title, author, categoryStockBox, submit);

        Scene scene = new Scene(form, 350, 250);
        popup.setScene(scene);
        popup.initOwner(tableView.getScene().getWindow());
        popup.show();
    }

    private void openEditBookWindow() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Edit Buku");
        idDialog.setHeaderText("Masukkan ID Buku yang akan diedit:");
        idDialog.setContentText("ID Buku:");

        idDialog.showAndWait().ifPresent(idInput -> {
            Book bookToEdit = BookUtil.findBookById(idInput.trim());

            if (bookToEdit == null) {
                showAlert("Buku tidak ditemukan", "ID buku " + idInput + " tidak ditemukan di database.");
                return;
            }

            // Show form with pre-filled values
            Stage popup = new Stage();
            popup.setTitle("Edit Buku");

            VBox form = new VBox(10);
            form.setPadding(new Insets(20));
            form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

            TextField titleField = createStyledTextField(bookToEdit.getTitle());
            TextField authorField = createStyledTextField(bookToEdit.getAuthor());
            TextField categoryField = createStyledTextField(bookToEdit.getCategory());
            TextField stockField = createStyledTextField(String.valueOf(bookToEdit.getStock()));

            HBox catStockBox = new HBox(10, categoryField, stockField);

            Button submit = createStyledButton("Simpan Perubahan", "#FFA500");
            submit.setOnAction(e -> {
                try {
                    String newTitle = titleField.getText().isEmpty() ? bookToEdit.getTitle() : titleField.getText();
                    String newAuthor = authorField.getText().isEmpty() ? bookToEdit.getAuthor() : authorField.getText();
                    String newCategory = categoryField.getText().isEmpty() ? bookToEdit.getCategory() : categoryField.getText();
                    int newStock = stockField.getText().isEmpty() ? bookToEdit.getStock() : Integer.parseInt(stockField.getText());

                    Book updatedBook = new Book(bookToEdit.getBookId(), newTitle, newAuthor, newCategory, newStock);
                    BookUtil.updateBook(updatedBook);
                    loadData();
                    showAlert("Sukses", "Data buku berhasil diperbarui.");
                    popup.close();
                } catch (NumberFormatException ex) {
                    showAlert("Input salah", "Stock harus berupa angka");
                }
            });

            form.getChildren().addAll(titleField, authorField, catStockBox, submit);

            Scene scene = new Scene(form, 400, 300);
            popup.setScene(scene);
            popup.initOwner(tableView.getScene().getWindow());
            popup.show();
        });
    }


    private void openDeleteBookWindow() {
        Stage popup = new Stage();
        popup.setTitle("Hapus Buku");

        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label idLabel = new Label("ID/Nama Buku");
        TextField idDelete = createStyledTextField("");
        VBox idFieldDeleteGroup = new VBox(5, idLabel, idDelete);

        Button submit = createStyledButton("Hapus", "#FFA500");
        submit.setOnAction(e -> {
            deleteBook(idDelete.getText());
            popup.close();
        });

        form.getChildren().addAll(idFieldDeleteGroup, submit);

        Scene scene = new Scene(form, 300, 150);
        popup.setScene(scene);
        popup.initOwner(tableView.getScene().getWindow());
        popup.show();
    }

    private void createTableColumns() {
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

        idCol.setPrefWidth(60);
        titleCol.setPrefWidth(250);
        authorCol.setPrefWidth(200);
        categoryCol.setPrefWidth(180);
        stockCol.setPrefWidth(100);

        tableView.getColumns().addAll(idCol, titleCol, authorCol, categoryCol, stockCol);
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
                if (String.valueOf(book.getBookId()).contains(keyword.toLowerCase())
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


    private void addBook(String title, String author, String category, String stockText) {
        try {
            int stock = Integer.parseInt(stockText);
            PropertyBook pb = new PropertyBook(0, title, author, category, stock);
            data.add(pb);
            BookUtil.saveBook(new ArrayList<>(data.stream().map(PropertyBook::toBook).toList()));
            loadData();
        } catch (NumberFormatException e) {
            showAlert("Input salah", "Stock harus berupa angka");
        }
    }


    private void deleteBook(String id) {
        BookUtil.deleteBook(id);
        loadData();
    }

    private TextField createStyledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
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

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-font-weight: bold; " +
                "-fx-background-radius: 20; -fx-border-radius: 20; " +
                "-fx-border-color: " + color + "; -fx-padding: 10 20 10 20; " +
                "-fx-background-color: " + color + "; -fx-text-fill: white;");
        return btn;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
