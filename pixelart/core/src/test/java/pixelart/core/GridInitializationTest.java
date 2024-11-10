package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.canvas.Canvas;


class GridInitializationTest {
   private Grid grid;
    private static int DEFAULT_GRID_WIDTH = 5;
    private static int DEFAULT_GRID_HEIGHT = 5;
    private static int pixelSize = 10;

    @BeforeEach
    void setUp() {
        grid = new Grid(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT, pixelSize);
    }

    @Test
    void testGridSize() {
        assertEquals(DEFAULT_GRID_HEIGHT, grid.getgridSizeHeight());
        assertEquals(DEFAULT_GRID_WIDTH, grid.getgridSizeWidth());
    }

    @Test
    void testJSONGridSize() {
        assertEquals(DEFAULT_GRID_WIDTH, grid.getJsonGrid().length);
        for (int i = 0; i < DEFAULT_GRID_WIDTH; i++) {
            assertEquals(DEFAULT_GRID_HEIGHT, grid.getJsonGrid().length);
        }
    }

    @Test
    void testJSONGridContent() {
        for (int i = 0; i < DEFAULT_GRID_WIDTH; i++) {
            for (int j = 0; j < DEFAULT_GRID_HEIGHT; j++) {
                assertEquals("#FFFFFF", grid.getJsonGrid()[i][j].toString());  // Compare individual elements, not the whole row
            }
        }
    }

    @Test
    void testCanvasProperties() {
        for (int i = 0; i < DEFAULT_GRID_WIDTH; i++) {  // Loop through the full grid size
            for (int j = 0; j < DEFAULT_GRID_HEIGHT; j++) {
                Canvas canvas = grid.getPixel(i,j).getCanvas();
                assertNotNull(canvas);
                assertEquals(pixelSize, canvas.getWidth());
                assertEquals(pixelSize, canvas.getHeight());
            }
        }
    }
}


