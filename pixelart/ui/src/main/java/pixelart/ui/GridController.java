package pixelart.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

public class GridController {

    /** The width of the grid, it is 60 pixels wide. */
    public static final int GRID_SIZE_WIDTH = 60;

    /** The height of the gird, it is 34 pixels tall. */
    public static final int GRID_SIZE_HEIGHT = 34;

    /** The size of each pixel. One of our pixels uses 10 pixels on
     * your screen to represent it.
     */
    public static final int PIXEL_SIZE = 10;

    /** The grid that contains the pixel data. */
    private final Grid grid;

    /** The GridPane used to display the grid in UI. */
    private final GridPane gridPane;

    /** Handler for managaing the grid's state. */
    private final GridStateHandler gridStateHandler;

    /** The string responsible for containing the current colour. */
    private String currentColor = "#000000";

    /**
     * Constructs a new GridController for managing the grid's UI and state.
     *
     * @param theGrid The grid model holding pixel data.
     * @param theGridPane The GridPane to display the grid.
     * @param theGridStateHandler Manages the grid's state.
     */
    public GridController(
        final Grid theGrid,
        final GridPane theGridPane,
        final GridStateHandler theGridStateHandler) {

        this.grid = theGrid;
        this.gridPane = theGridPane;
        this.gridStateHandler = theGridStateHandler;
    }

    /**
     * Initializes and sets up the GridPane with clickable pixel canvases.
     */
    public void initializeGridPane() {
        Pixel[][] pixels = grid.getAllPixels();
        for (int r = 0; r < grid.getgridSizeHeight(); r++) {
            for (int c = 0; c < grid.getgridSizeWidth(); c++) {
                Canvas canvas = pixels[r][c].getCanvas();
                gridPane.add(canvas, c, r);
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> pixelClick(row, column, event));
            }
        }
    }

    /**
     * Handles pixel click events, updating color based on click type.
     *
     * @param row The row index of the clicked pixel.
     * @param column The column index of the clicked pixel.
     * @param event The mouse event that triggered the click.
     */
    protected void pixelClick(
        final int row,
        final int column,
        final MouseEvent event) {

        String colorToApply;
        if (event.getButton() == MouseButton.PRIMARY) {
            colorToApply = currentColor;
        } else {
            colorToApply = "#FFFFFF"; // Right click for eraser (white)
        }
        grid.getPixel(row, column).updateColor(colorToApply);
        saveCanvasToServer();
    }

    /**
     * Gets the GridStateHandler for managing the server state.
     *
     * @return The GridStateHandler instance.
     */
    public GridStateHandler getGridStateHandler() {
        return gridStateHandler;
    }

    /**
     * Saves the current canvas state to the server.
     */
    private void saveCanvasToServer() {
        try {
            String[][] currentGrid = grid.getJsonGrid();
            gridStateHandler.postCanvas(currentGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the current drawing color for primary clicks.
     *
     * @param color The color to set as the current drawing color.
     */
    public void setCurrentColor(final String color) {
        this.currentColor = color;
    }

    /**
     * Gets the current drawing color.
     *
     * @return The current drawing color.
     */
    protected String getCurrentColor() {
        return currentColor;
    }

    /**
     * Gets the grid height in pixels.
     *
     * @return The height of the grid.
     */
    protected int getGridHeight() {
        return GRID_SIZE_HEIGHT;
    }

    /**
     * Gets the grid width in pixels.
     *
     * @return The width of the grid.
     */
    protected int getGridWidth() {
        return GRID_SIZE_WIDTH;
    }
}

