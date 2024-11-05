package pixelart.ui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;
import java.io.IOException;

/**
 * AppController is the main controller class for our JavaFX application.
 * It controls the interaction between the user and our canvas.
 * The user is able to draw and erase on the different pixels.
 * This is done by using left and right mouse click.
 */
public class AppController {

    /** The main grid containing the canvas element.*/
    @FXML
    private GridPane gridPane;

    /** The grid model containing our pixeldata.*/
    private Grid grid;

    /** Managed saving and loading gridstate from server.*/
    private GridStateHandler gridStateHandler;

    /** Defines the dimensions of the grid
     *  Only if the retrieving from server fails.*/
    private final int gridSize = 100;

    /** Size of each pixel square in the grid.*/
    private final int pixelSize = 10;

    /** Stores the current state of each pixel in grid.*/
    private String[][] currentState;

    /**
     * Initializes the controller as well as the canvas
     * Tries to retrieve canvas from server. Else a new one.
     *
     * @throws IOException if there´s an error initializing
     * @throws InterruptedException if the loading from server process fails
     * */
    @FXML
    public void initialize() throws IOException, InterruptedException {
        try {
            this.gridStateHandler = new GridStateHandler();
            currentState = gridStateHandler.loadCanvas();
            grid = new Grid(currentState, pixelSize);

        } catch (Exception e) {
            this.grid = new Grid(gridSize, pixelSize);
        }
        initializeGridPane();
    }

    private void initializeGridPane() {
        Pixel[][] pixels = grid.getAllPixels();
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                Canvas canvas = pixels[r][c].getCanvas();
                gridPane.add(canvas, r, c);
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> pixelClick(row, column, event));
            }
        }
    }

    private void pixelClick(
            final int row,
            final int column,
            final MouseEvent event) {
        boolean isBlack = event.getButton() != MouseButton.SECONDARY;
        grid.getPixel(row, column).updateColor(isBlack);
        saveCanvasToServer();

    }

    private void saveCanvasToServer() {
        try {
            GridStateHandler service = new GridStateHandler();
            String[][] currentGrid = grid.getJsonGrid();
            service.postCanvas(currentGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
