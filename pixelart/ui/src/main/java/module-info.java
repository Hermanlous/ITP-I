module pixelart.ui {
    // Requires JavaFX modules for graphics
    requires javafx.fxml; //Requires FXML to build the UI
    requires pixelart.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires org.json;
    requires javafx.controls; // Requires pixelart.core to access the core logic.

    exports pixelart.ui; // Exports the pixelart.ui package to allow other modules access.
    opens pixelart.ui to javafx.fxml; //Opens
}