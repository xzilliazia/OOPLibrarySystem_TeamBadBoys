package com.librarysystem.UI;

import com.librarysystem.model.User;
import com.librarysystem.model.BorrowRecord;
import com.librarysystem.service.BookUtil;
import com.librarysystem.util.Session;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class StdDashboard extends Application {
    private TableView<BorrowRecord> tableView;
    private ObservableList<BorrowRecord> data;
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        this.currentUser = Session.currentUser;

        // Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/img/background.jpg").toExternalForm(), 1280, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 800, false, false, false, false)
        );
        root.setBackground(new Background(bg));

        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setAlignment(Pos.CENTER);
        sidebar.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 0 0 0 0;");
        sidebar.setPrefWidth(300);

        Label userLabel = new Label(Session.currentUser.getUsername());
        applyFont(userLabel, "18pt", true);

        Button btnPinjam = new Button("PINJAM");
        applyFont(btnPinjam, "14pt", true, "white", "black");
        btnPinjam.setOnAction(e -> {
            BorrowDashboard borrow = new BorrowDashboard();
            borrow.setCurrentUser(currentUser);
            borrow.setPreviousStage(stage);
            borrow.start(new Stage());
            stage.close();
        });

        Button btnExit = new Button("KELUAR");
        applyFont(btnExit, "14pt", true, "orange", "black");
        btnExit.setOnAction(e -> new LoginMenu(). show(stage));

        sidebar.getChildren().addAll(userLabel, btnPinjam, btnExit);
        root.setLeft(sidebar);

        // Table
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BorrowRecord, Void> colNumber = new TableColumn<>("No.");
        colNumber.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        colNumber.setPrefWidth(50);

        TableColumn<BorrowRecord, String> colTitle = new TableColumn<>("Judul");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BorrowRecord, String> colDate = new TableColumn<>("Tanggal Pinjam");
        colDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        tableView.getColumns().addAll(colNumber, colTitle, colDate);

        VBox tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(30));
        tableBox.setFillWidth(true);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        tableView.setMaxHeight(Double.MAX_VALUE);
        tableBox.getChildren().addAll(tableView);

        root.setCenter(tableBox);

        refreshTable();

        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("User Dashboard");
        stage.show();
    }

    private void refreshTable() {
        // Make sure BookUtil provides this method properly
        List<BorrowRecord> records = BookUtil.getBorrowsForUser(currentUser.getUserId());
        data = FXCollections.observableArrayList(records);
        tableView.setItems(data);
    }

    private void applyFont(Label label, String size, boolean bold) {
        String weight = bold ? "bold" : "normal";
        label.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size + "; -fx-font-weight: " + weight + "; -fx-text-fill: black;");
    }

    private void applyFont(Button button, String size, boolean bold, String bgColor, String textColor) {
        String weight = bold ? "bold" : "normal";
        button.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: " + size + "; -fx-font-weight: " + weight +
                "; -fx-background-color: " + bgColor + "; -fx-text-fill: " + textColor + "; -fx-background-radius: 20; -fx-padding: 10 30;");
    }
}
