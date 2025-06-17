package com.librarysystem.UI;

import com.librarysystem.model.Book;
import com.librarysystem.service.BookUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BookManager extends Application {
    private TableView<PropertyBook> tableView;
    private ObservableList<PropertyBook> data;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Library Book Manager");

        //inisiasi agar editable
        tableView = new TableView();
        tableView.setEditable(true);

        //kolom tabel
        TableColumn<PropertyBook, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setOnEditCommit(event -> {
            PropertyBook pb = event.getRowValue();
            pb.idProperty().set(event.getNewValue());
            BookUtil.updateBook(pb.toBook());
        });

        TableColumn<PropertyBook, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(cell -> cell.getValue().titleProperty());
        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colTitle.setOnEditCommit(event -> {
            PropertyBook pb = event.getRowValue();
            pb.titleProperty().set(event.getNewValue());
        });

        TableColumn<PropertyBook, String> colAuthor = new TableColumn<>("Author");
        colAuthor.setCellValueFactory(cell -> cell.getValue().authorProperty());
        colAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        colAuthor.setOnEditCommit(event -> {
            PropertyBook pb = event.getRowValue();
            pb.authorProperty().set(event.getNewValue());
        });

        TableColumn<PropertyBook, String> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(cell -> cell.getValue().categoryProperty());
        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        colCategory.setOnEditCommit(event -> {
            PropertyBook pb = event.getRowValue();
            pb.categoryProperty().set(event.getNewValue());
        });

        TableColumn<PropertyBook, Integer> colStock = getPropertyBookIntegerTableColumn();

        //not editable
        idCol.setReorderable(false);
        colTitle.setReorderable(false);
        colAuthor.setReorderable(false);
        colCategory.setReorderable(false);
        colStock.setReorderable(false);

        //kolom ke tabelview
        tableView.getColumns().addAll(idCol, colTitle, colAuthor, colCategory, colStock);
        loadData();

        //Tombol tombol
        Button btnAddBook = new Button("Add New Book");
        btnAddBook.setOnAction(event -> addNewBook());
        Button btnDeleteBook = new Button("Delete Book");
        btnDeleteBook.setOnAction(event -> deleteSelectedBook());
        Button btnSaveBook = new Button("Save Changes");
        btnSaveBook.setOnAction(event -> saveChanges());

        HBox controls = new HBox(10, btnAddBook, btnDeleteBook, btnSaveBook);

        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setBottom(controls);

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();

    }
    //saya pisah kolom stoknya
    private TableColumn<PropertyBook, Integer> getPropertyBookIntegerTableColumn() {
        TableColumn<PropertyBook, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(cell -> cell.getValue().stockProperty().asObject());
        colStock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colStock.setOnEditCommit(event -> {
            PropertyBook pb = event.getRowValue();
            int newStock = event.getNewValue().intValue();
            // Validasi stock tidak boleh negatif
            if (newStock >= 0) {
                pb.stockProperty().set(newStock);
            } else {
                // Jika input tidak valid, refresh tabel untuk revert perubahan
                tableView.refresh();
            }
        });
        return colStock;
    }

    private void loadData() {
        ArrayList<Book> books = BookUtil.loadBooks();
        ArrayList<PropertyBook> propertyBooks = PropertyBook.bookToBind(books);
        data = FXCollections.observableArrayList(propertyBooks);
        tableView.setItems(data);
    }

    private void addNewBook() {
        Dialog<PropertyBook> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");

        Label lblId = new Label("ID: ");
        TextField tfId = new TextField();

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
        grid.add(lblId, 0, 0);
        grid.add(tfId, 1, 0);
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
                    return new PropertyBook(tfId.getText(), tfTitle.getText(), tfAuthor.getText(), tfCategory.getText(), stock);
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
            Alert alert = new Alert(Alert.AlertType.WARNING, "No book selected to delete.");
            alert.showAndWait();
        }
    }

    private void saveChanges() {
        ArrayList<PropertyBook> propertyBooks = new ArrayList<>(data);
        ArrayList<Book> books = new ArrayList<>();
        for (PropertyBook pb : propertyBooks) {
            books.add(new Book(pb.getId(), pb.getTitle(), pb.getAuthor(), pb.getCategory(), pb.getStock()));
        }
        BookUtil.saveBook(books);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Books saved successfully.");
        alert.showAndWait();
    }
}