package com.librarysystem.model;

import com.librarysystem.UI.PropertyBook;
import com.librarysystem.service.BookUtil;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;


public class Admin extends User {

    public void menu(Stage stage) {
        //pencarian buku bt judul
        TextField searchField = new TextField();
        searchField.setPromptText("Cari...");

        //data buku yang ditampilkan
        ArrayList<PropertyBook> propertyBooks = PropertyBook.bookToBind(User.getBookList());

        //bikin tabel dari metode User
        TableView<PropertyBook> tableView = ctTableView(propertyBooks);
        tableView.setPrefHeight(350);//batas tinmggi
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//kolom otomatis menyesuaikan

        //listener untuk pencarian
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue == null || newValue.isEmpty()) {
               //Tampilkan semua jika kosong
               tableView.setItems(FXCollections.observableArrayList(propertyBooks));
           } else {
               //cari judul buku njing
               ArrayList<Book> filteredBooks = BookUtil.searchBooksByTitle(newValue);
               ArrayList<PropertyBook> filteredPropertyBooks = PropertyBook.bookToBind(filteredBooks);
               tableView.setItems(FXCollections.observableArrayList(filteredPropertyBooks));
           }
        });

        //pembungkus tabel
        VBox tableContainer = new VBox(searchField, tableView);
        tableContainer.setAlignment(Pos.TOP_CENTER);
        tableContainer.setPadding(new Insets(10));//jarak pinggir vbox
        tableContainer.setSpacing(10);
        tableContainer.setStyle("-fx-border-color: lightgray;  -fx-border-width: 1;");

        //root layout utama
        VBox root = new VBox(tableContainer);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(20, 20, 0, 20));

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.setTitle("Admin Menu - Daftar Buku");
        stage.show();
    }

    public static void login(Stage stage) {
        Label userLabel = new Label("Username: ");
        TextField userField = new TextField();
        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();

            //not ready
        });
    }
}
