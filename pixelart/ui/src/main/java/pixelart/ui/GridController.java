package pixelart.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

public class GridController {
    public static final int GRID_SIZE_WIDTH = 60;
    public static final int GRID_SIZE_HEIGHT = 40;
    public static final int PIXEL_SIZE = 10;

    final Grid grid;
    private final GridPane gridPane;
    private final GridStateHandler gridStateHandler;
    private String currentColor = "#000000";

    public GridController(Grid grid, GridPane gridPane, GridStateHandler gridStateHandler) {
        this.grid = grid;
        this.gridPane = gridPane;
        this.gridStateHandler = gridStateHandler;
    }

    public void initializeGridPane() {
        Pixel[][] pixels = grid.getAllPixels();
        for (int r = 0; r < GRID_SIZE_HEIGHT; r++) {
            for (int c = 0; c < GRID_SIZE_WIDTH; c++) {
                Canvas canvas = pixels[r][c].getCanvas();
                gridPane.add(canvas, c, r);
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> pixelClick(row, column, event));
            }
        }
    }

    protected void pixelClick(final int row, final int column, final MouseEvent event) {
        String colorToApply;
        if (event.getButton() == MouseButton.PRIMARY) {
            colorToApply = currentColor;
        } else {
            colorToApply = "#FFFFFF"; // Right click for eraser (white)
        }
        grid.getPixel(row, column).updateColor(colorToApply);
        saveCanvasToServer();
    }

    public GridStateHandler getGridStateHandler() {
        return gridStateHandler;
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

    protected String getCurrentColor() {
        return currentColor;
    }

    protected int getGridHeight() {
        return GRID_SIZE_HEIGHT;
    }

    protected int getGridWidth() {
        return GRID_SIZE_WIDTH;
    }
}

