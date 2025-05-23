module com.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.librarysystem to javafx.fxml;
    exports com.librarysystem;
    exports com.librarysystem.controller;
    opens com.librarysystem.controller to javafx.fxml;
}