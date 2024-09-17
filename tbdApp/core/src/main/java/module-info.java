module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens app to javafx.graphics, javafx.fxml;
}
