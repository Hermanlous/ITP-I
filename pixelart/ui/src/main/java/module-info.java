module pixelart.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;

    opens pixelart.ui to javafx.graphics, javafx.fxml;
}