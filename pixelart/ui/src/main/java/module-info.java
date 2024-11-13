module pixelart.ui {
    requires javafx.fxml;
    requires pixelart.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires org.json;
    requires javafx.controls;
    requires javafx.graphics;
    requires com.github.spotbugs.annotations;

    exports pixelart.ui;
    opens pixelart.ui to javafx.fxml;
}