package pixelart.ui;

//import org.checkerframework.checker.units.qual.g; why did we have this?

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

public class AppController {
    
/**
 * AppController is the main controller class for the JavaFX application pixelart.
 * It controls the interaction between the user and the 100x100 (may change) grid of canvases.
 * The users can draw or erase on the different pixels  using left and right mouse clicks. More to come.
 * Which pixels that are coloured in are saved in a text file.
*/
    @FXML
    private GridPane gridPane;

    private Grid grid;
    private GridStateHandler gridStateHandler;
    private final int gridSize = 100;
    private final int pixelSize = 10;

    @FXML
    public void initialize() {

        grid = new Grid(gridSize, pixelSize);
        gridStateHandler = new GridStateHandler(grid);
        Pixel[][] pixelGrid = grid.getAllPixels(); 

        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                Canvas canvas = pixelGrid[r][c].getCanvas();
                gridPane.add(canvas, r, c);
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> pixelClick(row, column, event));
            }
        }

        gridStateHandler.loadState();
    }

    private void pixelClick(int row, int column, MouseEvent event) {
        boolean isBlack = event.getButton() != MouseButton.SECONDARY;
        grid.getPixel(row, column).updateColor(isBlack);; 
    }

}