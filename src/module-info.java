module film {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    exports primaryPage to javafx.graphics;
    opens film to javafx.fxml;

    exports film;
    exports edit.controller;
    exports edit.Util;
    opens edit.controller to javafx.fxml;
    opens edit.Util to javafx.fxml;
}