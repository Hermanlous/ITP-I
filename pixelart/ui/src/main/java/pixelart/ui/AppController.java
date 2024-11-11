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

    /** 
     * The main grid containing the canvas element.
     */
    @FXML
    private GridPane gridPane;

    /**
     * The color palette displayed as a set of color buttons.
     */
    @FXML
    private HBox colorPalette;

    /** 
     * The grid model containing our pixel data.
     */
    private Grid grid;

    /** 
     * Manages saving and loading the grid state from the server.
     */
    private GridStateHandler gridStateHandler;

    /** 
     * Manages the grid and interactions with the canvas. 
     */
    private GridManager gridManager;

    /** 
     * Manages the color palette and color selection. 
     */
    private ColorManager colorManager;

    /**
     * Initializes the controller, sets up the grid and color management.
     *
     * @throws IOException if there's an error initializing
     * @throws InterruptedException if the loading from server process fails
     */
    @FXML
    public void initialize() throws IOException, InterruptedException {
        this.gridStateHandler = new GridStateHandler();

        // Initialize the grid
        try {
            this.grid = new Grid(GridManager.PIXEL_SIZE);
        } catch (Exception e) {
            this.grid = new Grid(GridManager.DEFAULT_GRID_HEIGHT, GridManager.DEFAULT_GRID_WIDTH, GridManager.PIXEL_SIZE);
        }

        // Initialize the grid manager
        this.gridManager = new GridManager(grid, gridPane, gridStateHandler);
        gridManager.initializeGridPane();

        // Initialize the color manager
        this.colorManager = new ColorManager(colorPalette);
        colorManager.initializeColorPalette();

        // Connect the color selection to the grid manager
        colorManager.setOnColorSelected(gridManager::setCurrentColor);
    }
}
