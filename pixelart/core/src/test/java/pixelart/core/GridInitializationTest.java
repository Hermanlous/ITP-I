package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import pixelart.core.GridManager;

class GridInitializationTest {
    private GridManager gridManager;
    private static int gridSize = 5;
    private static int pixelSize = 10;

    @BeforeEach
    void setUp() {
        gridManager = new GridManager(gridSize, pixelSize);
    }


    @Test
    void testGridSize() {
        gridManager.initializeGrid();
        assertEquals(gridSize, gridManager.getGrid().length);
        assertEquals(gridSize, gridManager.getGrid()[0].length);
    }

    @Test
    void testJSONGridSize() {
        gridManager.initializeGrid();
        assertEquals(gridSize, gridManager.getJsonGrid().length);
        for (int i = 0; i < gridSize; i++) {
            assertEquals(gridSize, gridManager.getJsonGrid().length);
        }
    }

    @Test
    void testJSONGridContent() {
        gridManager.initializeGrid();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                assertEquals("W", gridManager.getJsonGrid()[i].getString(j));  // Compare individual elements, not the whole row
            }
        }
    }

    @Test
    void testCanvasProperties() {
        gridManager.initializeGrid();
        for (int i = 0; i < gridSize; i++) {  // Loop through the full grid size
            for (int j = 0; j < gridSize; j++) {
                Canvas canvas = gridManager.getGrid()[i][j];
                assertNotNull(canvas);
                assertEquals(pixelSize, canvas.getWidth());
                assertEquals(pixelSize, canvas.getHeight());
            }
        }
    }
}



