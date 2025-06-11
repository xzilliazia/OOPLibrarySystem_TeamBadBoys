package com.librarysystem.model;

import com.librarysystem.UI.PropertyBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class User {
    private static ArrayList<Book> bookList = new ArrayList<>();

    public TableView<PropertyBook> ctTableView(ArrayList<PropertyBook> ary) {
        TableView<PropertyBook> tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.getColumns().clear();

        TableColumn<PropertyBook, String> idColom = new TableColumn<>("ID"); idColom.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<PropertyBook, String> titleColom = new TableColumn<>("Title"); titleColom.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<PropertyBook, String> authorColom = new TableColumn<>("Author"); authorColom.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<PropertyBook, String> categoryColom = new TableColumn<>("Category"); categoryColom.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<PropertyBook, Integer> stokColom = new TableColumn<>("Stock"); stokColom.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableView.getColumns().addAll(idColom, titleColom, authorColom, categoryColom, stokColom);

        //not edit able colum
        idColom.setReorderable(false);
        titleColom.setReorderable(false);
        authorColom.setReorderable(false);
        categoryColom.setReorderable(false);
        stokColom.setReorderable(false);

        GridPane grd = new GridPane();
        grd.setPadding(new Insets(10));
        grd.setVgap(10);
        grd.setHgap(10);
        grd.setAlignment(Pos.CENTER);

        final VBox v = new VBox();
        v.setSpacing(10);
        v.getChildren().addAll(tableView);
        grd.add(v, 0, 0);

        final ObservableList<PropertyBook> observableList = FXCollections.observableArrayList(ary);
        tableView.setItems(observableList);
        return tableView;

    }

    public static ArrayList<Book> getBookList() {
        return bookList;
    }
}
