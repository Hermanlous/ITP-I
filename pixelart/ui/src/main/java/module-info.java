module pixelart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;

    opens pixelart to javafx.graphics, javafx.fxml;
    opens pixelart.core to javafx.fxml, javafx.graphics;
}
