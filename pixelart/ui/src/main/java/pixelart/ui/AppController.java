package pixelart.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;

/**
 * AppController is the main controller class for our JavaFX application.
 * It controls the interaction between the user and our canvas.
 * The user is able to draw and erase on the different pixels.
 * This is done by using left and right mouse click.
 */
public class AppController {

    /** The main grid containing the canvas element. */
    @FXML
    private GridPane gridPane;

    /**
     * The color palette displayed as a set of color buttons.
     */
    @FXML
    private HBox colorPalette;

    /** The grid model containing our pixel data. */
    private Grid grid;

    /** Managed saving and loading grid state from the server. */
    private GridStateHandler gridStateHandler;

    /** The size of the width of the grid. */
    private final int gridSizeWidth = 60;

    /** The size of the height of the grid. */
    private final int gridSizeHeight = 40;

    /** Size of each pixel square in the grid. */
    private final int pixelSize = 10;

    private ColorManager colorManager;
    private GridManager gridManager;

    /**
     * Initializes the controller as well as the canvas.
     * Tries to retrieve the canvas from the server; otherwise, a new one is created.
     *
     * @throws IOException if there is an error during initialization.
     * @throws InterruptedException if the loading from server process fails.
     */
    @FXML
    public void initialize() throws IOException, InterruptedException {
        this.colorManager = new ColorManager(colorPalette);
        colorManager.initializeColorPalette();

        try {
            this.gridStateHandler = new GridStateHandler();
            String[][] currentState = gridStateHandler.loadCanvas();
            grid = new Grid(currentState, GridManager.PIXEL_SIZE);
        } catch (Exception e) {
            this.grid = new Grid(GridManager.GRID_SIZE_WIDTH, GridManager.GRID_SIZE_HEIGHT, GridManager.PIXEL_SIZE);
        }

        this.gridManager = new GridManager(grid, gridPane, gridStateHandler);
        gridManager.initializeGridPane();

        // Connect color selection to grid manager
        colorManager.setOnColorSelected(color -> gridManager.setCurrentColor(color));
    }
}
