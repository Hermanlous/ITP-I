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

    /** The main grid containing the canvas element.*/
    @FXML
    private GridPane gridPane;

    /**
     * The color palette displayed as a set of color buttons.
     */
    @FXML
    private HBox colorPalette;

    /** The grid model containing our pixeldata.*/
    private Grid grid;

    /** Managed saving and loading gridstate from server.*/
    private GridStateHandler gridStateHandler;

    /** The size of the width of the grid. */
    private final int gridSizeWidth = 60;

    /** The size of the height of the grid. */
    private final int gridSizeHeight = 40;

    /** Size of each pixel square in the grid.*/
    private final int pixelSize = 10;

    /** Stores the current state of each pixel in grid.*/
    private String[][] currentState;

    /** Manages color selection and palette operations. */
    private ColorController colorController;

    /** Controls the grid’s visual and interactive behavior. */
    private GridController gridController;

    /**
     * Initializes the controller as well as the canvas
     * Tries to retrieve canvas from server. Else a new one.
     *
     * @throws IOException if there´s an error initializing
     * @throws InterruptedException if the loading from server process fails
     * */

    @FXML
    public void initialize() throws IOException, InterruptedException {
        this.colorController = new ColorController(colorPalette);
        colorController.initializeColorPalette();

        try {
            this.gridStateHandler = new GridStateHandler();
            String[][] currentState = gridStateHandler.loadCanvas();
            grid = new Grid(currentState, GridController.PIXEL_SIZE);
        } catch (Exception e) {
            this.grid = new Grid(GridController.GRID_SIZE_WIDTH,
            GridController.GRID_SIZE_HEIGHT, GridController.PIXEL_SIZE);
        }

        this.gridController =
        new GridController(grid, gridPane, gridStateHandler);
        gridController.initializeGridPane();

        colorController.setOnColorSelected(
        color -> gridController.setCurrentColor(color));
    }

    /**
     * Retrieves the controller responsible for managing grid operations.
     *
     * @return the GridController instance managing the grid's behavior.
     */
    public GridController getGridController() {
        return gridController;
    }
}
