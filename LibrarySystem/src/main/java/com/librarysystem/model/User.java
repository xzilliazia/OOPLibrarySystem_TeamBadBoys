package com.librarysystem.model;

import com.librarysystem.action.Authenticatable;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.util.ArrayList;

public abstract class User {
    private static ArrayList<Book> bookList = new ArrayList<>();
    public abstract String updateBook(Stage stage);
    public void addBook(Book book) {
        bookList.add(book);
    }



}
