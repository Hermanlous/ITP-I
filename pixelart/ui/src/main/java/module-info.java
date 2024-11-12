module pixelart.ui {
    requires javafx.graphics; // Requires JavaFX modules for graphics 
    requires javafx.fxml; //Requires FXML to build the UI
    requires pixelart.core; // Requires pixelart.core to access the core logic. 

    exports pixelart.ui; // Exports the pixelart.ui package to allow other modules access.
    opens pixelart.ui to javafx.fxml; //Opens
}