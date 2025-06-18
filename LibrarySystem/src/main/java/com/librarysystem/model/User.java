package com.librarysystem.model;

import com.librarysystem.UI.PropertyBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public abstract class User {
    private int userId;
    private String username;
    private String major;
    private String role;

    private static ArrayList<Book> bookList = new ArrayList<>();

    // Constructor
    public User(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public User(String username, String major) {
        this.username = username;
        this.major = major;
    }

    public User(int userId, String username, String major, String role) {
        this.userId = userId;
        this.username = username;
        this.major = major;
        this.role = role;
    }

    // Getter & Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter & Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMajor() {return major;}

    public void setMajor(String major) {this.major = major;}

    // Getter & Setter for role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Book list getter
    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    // Helper: create TableView
    public TableView<PropertyBook> ctTableView(ArrayList<PropertyBook> ary) {
        TableView<PropertyBook> tableView = new TableView<>();
        tableView.setEditable(false);

        // Define columns
        TableColumn<PropertyBook, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PropertyBook, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<PropertyBook, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<PropertyBook, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<PropertyBook, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Add columns
        tableView.getColumns().addAll(idColumn, titleColumn, authorColumn, categoryColumn, stockColumn);

        // Prevent columns from being reordered
        for (TableColumn<?, ?> col : tableView.getColumns()) {
            col.setReorderable(false);
        }

        // Set data
        ObservableList<PropertyBook> observableList = FXCollections.observableArrayList(ary);
        tableView.setItems(observableList);

        return tableView;
    }
}
