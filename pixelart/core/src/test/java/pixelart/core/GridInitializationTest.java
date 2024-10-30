package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.canvas.Canvas;
import pixelart.core.GridManager;

class GridInitializationTest {
    private GridManager gridManager;
    private static final int gridSizeHeight = 5;
    private static final int gridSizeWidth = 7;
    private static final int pixelSize = 10;

    @BeforeEach
    void setUp() {
        gridManager = new GridManager(gridSizeHeight, gridSizeWidth, pixelSize);
    }

    @Test
    void testGridSize() {
        gridManager.initializeGrid();
        assertEquals(gridSizeHeight, gridManager.getGrid().length);
        assertEquals(gridSizeWidth, gridManager.getGrid()[0].length);
    }

    @Test
    void testJSONGridSize() {
        gridManager.initializeGrid();
        assertEquals(gridSizeHeight, gridManager.getJsonGrid().length);
        for (int i = 0; i < gridSizeHeight; i++) {
            assertEquals(gridSizeWidth, gridManager.getJsonGrid()[i].length());
        }
    }

    @Test
    void testJSONGridContent() {
        gridManager.initializeGrid();
        for (int i = 0; i < gridSizeHeight; i++) {
            for (int j = 0; j < gridSizeWidth; j++) {
                assertEquals("W", gridManager.getJsonGrid()[i].getString(j));  // Compare individual elements, not the whole row
            }
        }
    }

    @Test
    void testCanvasProperties() {
        gridManager.initializeGrid();
        for (int i = 0; i < gridSizeHeight; i++) {
            for (int j = 0; j < gridSizeWidth; j++) {
                Canvas canvas = gridManager.getGrid()[i][j];
                assertNotNull(canvas);
                assertEquals(pixelSize, canvas.getWidth());
                assertEquals(pixelSize, canvas.getHeight());
            }
        }
    }
}
