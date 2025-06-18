package com.librarysystem;

import com.librarysystem.UI.BookManager;
import com.librarysystem.UI.BorrowDashboard;
import com.librarysystem.UI.LoginMenu;
import com.librarysystem.UI.StdDashboard;
import com.librarysystem.model.Book;
import com.librarysystem.model.Admin;
import com.librarysystem.model.User;
import com.librarysystem.service.BookUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

//Admin test function
//public class MainApp extends Application {
//    public static void main(String[] args) {
//       ArrayList<Book> loadedBooks = BookUtil.loadBooks();
//        User.getBookList().addAll(loadedBooks);
//
//        launch(args);
//   }
//
//   @Override
//public void start(Stage stage) throws Exception {
//     Admin admin = new Admin();
//        admin.menu(stage);
//    }
//}


//BookManager test function
////BookManager test function
//public class MainApp extends Application {
//    @Override
//    public void start(Stage primaryStage) {
//        BookManager bookManager = new BookManager();
//        Scene scene = bookManager.createScene();
//
//        primaryStage.setTitle("Manajemen Buku");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

//Login panel test
//public class MainApp extends Application {
//    @Override
//    public void start(Stage stage) {
//        new LoginMenu().show(stage);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

//Login panel test
public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        new LoginMenu().show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//public class MainApp{
//    public static void main(String[] args) {
//        Application.launch(StdDashboard.class, args);
//    }
//}
//public class MainApp{
//    public static void main(String[] args) {
//        BorrowDashboard.launch(BorrowDashboard.class, args);
//    }
//}