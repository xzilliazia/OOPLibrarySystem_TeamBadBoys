package com.librarysystem.model;

import com.librarysystem.books.Book;
import com.librarysystem.UI.ProBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public abstract class User {
    private static ArrayList<Book> bookList = new ArrayList<>();
    public abstract String updateBook(Stage stage);
    public void addBook(Book book) {
        bookList.add(book);
    }
    public TableView<ProBook> ctTableView(ArrayList<ProBook> ary) {
        TableView<ProBook> tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.getColumns().clear();

        TableColumn<ProBook, String> idColom = new TableColumn<>("id"); idColom.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<ProBook, String> titleColom = new TableColumn<>("Title"); titleColom.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<ProBook, String> authorColom = new TableColumn<>("Author"); authorColom.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<ProBook, String> categoryColom = new TableColumn<>("Category"); categoryColom.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<ProBook, Integer> stokColom = new TableColumn<>("Stok"); stokColom.setCellValueFactory(new PropertyValueFactory<>("stok"));

        tableView.getColumns().addAll(idColom, titleColom, authorColom, categoryColom, stokColom);

        GridPane grd = new GridPane();
        grd.setPadding(new Insets(10));
        grd.setVgap(10);
        grd.setHgap(10);
        grd.setAlignment(Pos.CENTER);

        final VBox v = new VBox();
        v.setSpacing(10);
        v.getChildren().addAll(tableView);
        grd.add(v, 0, 0);

        final ObservableList<ProBook> observableList = FXCollections.observableArrayList(ary);
        tableView.setItems(observableList);
        return tableView;

    }

    public static ArrayList<Book> getBookList() {
        return bookList;
    }
}
