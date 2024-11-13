module pixelart.core {
    requires javafx.graphics; // JavaFX module required for showing graphics, including the canvas grid.
    requires org.json;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.web;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.github.spotbugs.annotations;

    exports pixelart.core; // Exports the pixelart.core package to make it usable to other modules. 
}