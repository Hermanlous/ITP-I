package pixelart.ui;

//import org.checkerframework.checker.units.qual.g;
//TODO why do we have this

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

/**
 * AppController is the main controller class for the JavaFX
 * application pixelart. It controls the interaction between
 * the user and the 100x100 (may change) grid of canvases.
 * The users can draw or erase on the different pixels
 * using left and right mouse clicks. More to come.
 * Which pixels that are coloured in are saved in a text file.
*/

public class AppController {

    /**
     * The grid pane that holds the pixel canvases.
     */
    @FXML
    private GridPane gridPane;

    /**
     * The grid that manages the pixel data and state.
     */
    private Grid grid;

    /**
     * The handler managing the state of the grid,
     * including saving and loading.
     */
    private GridStateHandler gridStateHandler;

    /**
     * The size of the grid, it has 100 by 100 pixels.
     */
    private final int gridSize = 100;

    /**
     * The size of each pixel in the grid.
     * Each square the app has, use 10x10 pixels on screen to represent it.
     */
    private final int pixelSize = 10;

    /**
     * Initializes the grid and sets up the event handlers for each pixel.
     * Creates a 100x100 grid of canvases.
     * Loads the previous grid state.
     */
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
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                pixelClick(row, column, event));
            }
        }
        gridStateHandler.loadState();
    }

    /**
     * Handles mouse click events on a pixel to update the colour.
     *
     * @param row the row index of the pixel clicked.
     * @param column the column index of the pixel clicked.
     * @param event the mouse event triggered by the click.
     */
    private void pixelClick(
        final int row, final int column, final MouseEvent event) {
        boolean isBlack = event.getButton() != MouseButton.SECONDARY;
        grid.getPixel(row, column).updateColor(isBlack);
    }
}
