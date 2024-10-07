module pixelart.core {
    requires javafx.graphics; // JavaFX module required for showing graphics, including the canvas grid.
    requires org.json; // JSON library required for saving/ and loading the grid state in JSON format.

    exports pixelart.core; // Exports the pixelart.core package to make it usable to other modules. 
}