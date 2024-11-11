package pixelart.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

public class GridManager {
    public static final int PIXEL_SIZE = 10;

    /** 
     * Default size of the width of the grid if loading fails.
     */
    public static final int DEFAULT_GRID_WIDTH = 60;

    /**
     * Default size of the height of the grid if loading fails. 
     */
    public static final int DEFAULT_GRID_HEIGHT = 40;

    private final Grid grid;
    private final GridPane gridPane;
    private final GridStateHandler gridStateHandler;
    private String currentColor = "#000000"; // set black as default color

    public GridManager(Grid grid, GridPane gridPane, GridStateHandler gridStateHandler) {
        this.grid = grid;
        this.gridPane = gridPane;
        this.gridStateHandler = gridStateHandler;
    }

    public void initializeGridPane() {
        // Clear existing grid pane
        gridPane.getChildren().clear();

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

    private void pixelClick(final int row, final int column, final MouseEvent event) {
        String colorToApply;
        if (event.getButton() == MouseButton.PRIMARY) {
            colorToApply = currentColor;
        } else {
            colorToApply = "#FFFFFF"; // Right click for eraser (white)
        }
        grid.getPixel(row, column).updateColor(colorToApply);
        saveCanvasToServer();
    }

    private void saveCanvasToServer() {
        try {
            String[][] currentGrid = grid.getJsonGrid();
            gridStateHandler.postCanvas(currentGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentColor(String color) {
        this.currentColor = color;
    }
}
