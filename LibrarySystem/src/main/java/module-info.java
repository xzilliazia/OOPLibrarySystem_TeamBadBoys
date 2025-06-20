module com.librarysystem {
    requires javafx.controls;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.logging;
    requires javafx.fxml;

    opens com.librarysystem to javafx.fxml;
    exports com.librarysystem;
    exports com.librarysystem.controller;
    exports com.librarysystem.UI;
    opens com.librarysystem.controller to javafx.fxml;
    opens com.librarysystem.model to javafx.base;

}