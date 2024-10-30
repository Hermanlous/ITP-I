package pixelart.ui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import pixelart.core.GridManager;
import java.io.IOException;

public class AppController {
    
/**
 * AppController is the main controller class for the JavaFX application pixelart.
 * It controls the interaction between the user and the 100x100 (may change) grid of canvases.
 * The users can draw or erase on the different pixels  using left and right mouse clicks. More to come.
 * Which pixels that are coloured in are saved in a text file.
*/
    @FXML
    private GridPane gridPane;

    private GridManager gridManager;
    private final int gridSizeWidth = 100;
    private final int gridSizeHeight = 100;
    private final int pixelSize = 10;

    @FXML
    public void initialize() {
        gridManager = new GridManager(gridSizeHeight, gridSizeWidth, pixelSize);
        Canvas[][] grid = gridManager.getGrid();

        for (int r = 0; r < gridSizeHeight; r++) {
            for (int c = 0; c < gridSizeWidth; c++) {
                Canvas canvas = grid[r][c];
                gridPane.add(canvas, r, c);
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> pixelClick(row, column, event));
            }
        }

        try {
            gridManager.loadState();
        } catch (IOException e) {
            System.err.println("Error loading saved state: " + e.getMessage());
            // maybe add some ui feedback here
        }
    }

    private void pixelClick(int row, int column, MouseEvent event) {
        boolean isBlack = event.getButton() != MouseButton.SECONDARY;
        gridManager.updatePixel(row, column, isBlack); 
    }

}