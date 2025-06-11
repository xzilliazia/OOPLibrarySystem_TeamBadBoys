package com.librarysystem;

import com.librarysystem.UI.BookManager;
import com.librarysystem.model.Book;
import com.librarysystem.model.Admin;
import com.librarysystem.model.User;
import com.librarysystem.service.BookUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

//Admin test function
//public class MainApp extends Application {
//    public static void main(String[] args) {
//        ArrayList<Book> loadedBooks = BookUtil.loadBooks();
//        User.getBookList().addAll(loadedBooks);
//
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        Admin admin = new Admin();
//        admin.menu(stage);
//    }
//}

////BookManager test function
public class MainApp{
    public static void main(String[] args) {
        Application.launch(BookManager.class, args);
    }
}