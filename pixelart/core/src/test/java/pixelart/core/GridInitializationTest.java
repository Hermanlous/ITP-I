package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.canvas.Canvas;


/*class GridInitializationTest {
   /private Grid grid;
    private static int gridSize = 5;
    private static int pixelSize = 10;

    @BeforeEach
    void setUp() {
        grid = new Grid(gridSize, pixelSize);
    }


    @Test
    void testGridSize() {
        assertEquals(gridSize, grid.getAllPixels().length);
        assertEquals(gridSize, grid.getAllPixels()[0].length);
    }

    @Test
    void testJSONGridSize() {
        assertEquals(gridSize, grid.getJsonGrid().length);
        for (int i = 0; i < gridSize; i++) {
            assertEquals(gridSize, grid.getJsonGrid().length);
        }
    }

    @Test
    void testJSONGridContent() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                assertEquals("W", grid.getJsonGrid()[i][j].toString());  // Compare individual elements, not the whole row
            }
        }
    }

    @Test
    void testCanvasProperties() {
        for (int i = 0; i < gridSize; i++) {  // Loop through the full grid size
            for (int j = 0; j < gridSize; j++) {
                Canvas canvas = grid.getPixel(i,j).getCanvas();
                assertNotNull(canvas);
                assertEquals(pixelSize, canvas.getWidth());
                assertEquals(pixelSize, canvas.getHeight());
            }
        }
    }
}*/



