package com.librarysystem.UI;

import com.librarysystem.model.Book;
import com.librarysystem.service.BookUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;

public class BookManager extends Application {
    private TableView<PropertyBook> tableView;
    private ObservableList<PropertyBook> data;

    @Override
    public void start(Stage stage) {
        // Root with background
        BorderPane root = new BorderPane();
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/img/background.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bgImage));

        // LEFT PANEL
        VBox leftPanel = createControlPanel();
        leftPanel.setPrefWidth(400);
        root.setLeft(leftPanel);

        // TABLEVIEW
        tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white;");
        createTableColumns();
        loadData();

        VBox tableBox = new VBox(tableView);
        tableBox.setPadding(new Insets(20));
        root.setCenter(tableBox);

        // SCENE
        Scene scene = new Scene(root, 1280, 800);
        stage.setTitle("UMM Library - Book Manager(ADMIN)");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createControlPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 20;");

        TextField searchField = createStyledTextField("Nama Buku/ID Buku");
        Button searchBtn = createStyledButton("cari", "orange");
        searchBtn.setOnAction(e -> searchBooks(searchField.getText()));

        Label lblAdd = new Label("TAMBAH BUKU");
        TextField title = createStyledTextField("Nama Buku");
        TextField author = createStyledTextField("Author");
        TextField category = createStyledTextField("Kategori");
        TextField stock = createStyledTextField("Stock");
        Button btnAdd = createStyledButton("tambah", "orange");
        btnAdd.setOnAction(e -> addBook(title.getText(), author.getText(), category.getText(), stock.getText()));

        Label lblDelete = new Label("HAPUS BUKU");
        TextField idDelete = createStyledTextField("ID");
        Button btnDelete = createStyledButton("hapus buku", "orange");
        btnDelete.setOnAction(e -> deleteBook(idDelete.getText()));

        panel.getChildren().addAll(
                searchField, searchBtn,
                lblAdd, title, author, category, stock, btnAdd,
                lblDelete, idDelete, btnDelete
        );
        return panel;
    }

    private void createTableColumns() {
        TableColumn<PropertyBook, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colId.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<PropertyBook, String> colTitle = new TableColumn<>("NAMA BUKU");
        colTitle.setCellValueFactory(cell -> cell.getValue().titleProperty());
        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<PropertyBook, String> colAuthor = new TableColumn<>("AUTHOR");
        colAuthor.setCellValueFactory(cell -> cell.getValue().authorProperty());
        colAuthor.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<PropertyBook, String> colCategory = new TableColumn<>("KATEGORI");
        colCategory.setCellValueFactory(cell -> cell.getValue().categoryProperty());
        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<PropertyBook, Integer> colStock = new TableColumn<>("STOK");
        colStock.setCellValueFactory(cell -> cell.getValue().stockProperty().asObject());
        colStock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tableView.getColumns().addAll(colId, colTitle, colAuthor, colCategory, colStock);
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

    private void addNewBook() {
        Dialog<PropertyBook> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");

        Label lblTitle = new Label("Title: ");
        TextField tfTitle = new TextField();

        Label lblAuthor = new Label("Author: ");
        TextField tfAuthor = new TextField();

        Label lblCategory = new Label("Category: ");
        TextField tfCategory = new TextField();

        Label lblStock = new Label("Stock: ");
        TextField tfStock = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblTitle, 0, 1);
        grid.add(tfTitle, 1, 1);
        grid.add(lblAuthor, 0, 2);
        grid.add(tfAuthor, 1, 2);
        grid.add(lblCategory, 0, 3);
        grid.add(tfCategory, 1, 3);
        grid.add(lblStock, 0, 4);
        grid.add(tfStock, 1, 4);

        dialog.getDialogPane().setContent(grid);
        ButtonType btnAddType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAddType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAddType) {
                try {
                    int stock = Integer.parseInt(tfStock.getText());
                    if (stock < 0) throw new NumberFormatException();
                    return new PropertyBook("", tfTitle.getText(), tfAuthor.getText(), tfCategory.getText(), stock);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Stock must be a non-negative integer.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
        dialog.showAndWait().ifPresent(newBook -> {
            data.add(newBook);
            saveChanges();
            tableView.refresh();
        });
    }

    private void deleteSelectedBook() {
        PropertyBook selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            //hapus dari file
            BookUtil.deleteBook(selected.getId());
            //hapus dari tabel view
            tableView.getItems().remove(selected);

        } else {
            ArrayList<Book> found = BookUtil.searchBooksByTitle(keyword);
            ArrayList<PropertyBook> props = PropertyBook.bookToBind(found);
            tableView.setItems(FXCollections.observableArrayList(props));
        }
    }

    private void addBook(String title, String author, String category, String stockText) {
        try {
            int stock = Integer.parseInt(stockText);
            PropertyBook pb = new PropertyBook("", title, author, category, stock);
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
        tf.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-background-color: white;");
        return tf;
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-font-weight: bold; -fx-background-radius: 20; -fx-border-radius: 20; " +
                "-fx-border-color: " + color + "; -fx-padding: 10; -fx-background-color: " + color + ";");
        return btn;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
