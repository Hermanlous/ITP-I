package pixelart.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*public class WriteJsonTest {

    private Grid grid;
    private GridStateHandler gridStateHandler;
    private static final int gridSize = 5;
    private static final int pixelSize = 10;

    @BeforeEach
    public void setUp() {
    // First initialize the Grid
    this.grid = new Grid(gridSize, pixelSize);
    // Then initialize the GridStateHandler with the already created grid
    this.gridStateHandler = new GridStateHandler(); // Now we can create GridStateHandler
    // Finally, reinitialize the Grid with the GridStateHandler
    this.grid = new Grid(gridSize, pixelSize);
    }

    @Test
    public void testSaveJsonState() throws Exception {
        // Mock a grid filled with black pixels
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                grid.getPixel(row, col).updateColor(true); // Set to black
            }
        }

        // Set the filepath for saving
        Field filepathField = GridStateHandler.class.getDeclaredField("filepathJson");
        filepathField.setAccessible(true);
        filepathField.set(gridStateHandler, "testJsonCanvas.json");

        // Save the grid state
        gridStateHandler.saveJsonState();
        File file = new File("testJsonCanvas.json");
        assertTrue(file.exists(), "Output file should exist");

        // Read the saved file
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        }

        // Create the expected JSON
        JSONArray[] testJsonGrid = new JSONArray[gridSize];
        for (int row = 0; row < gridSize; row++) {
            testJsonGrid[row] = new JSONArray();
            for (int col = 0; col < gridSize; col++) {
                testJsonGrid[row].put("B"); // Black pixels
            }
        }
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("canvas", testJsonGrid);

        // Compare the actual and expected JSON
        JSONObject actualJson = new JSONObject(fileContent.toString());
        //assertEquals(expectedJson.toString(), actualJson.toString(), "Saved JSON should match the expected JSON");
    }

    @Test
    public void testUpdatePixel() {
        // Use a spy to check method calls
        GridStateHandler gridStateHandlerSpy = Mockito.spy(gridStateHandler);
        int row = 3;
        int col = 3;

        // Update a pixel to black
        grid.getPixel(row, col).updateColor(true);
        assertEquals("B", grid.getPixel(row, col).getColorAsString());

        // Update the same pixel to white
        grid.getPixel(row, col).updateColor(false);
        assertEquals("W", grid.getPixel(row, col).getColorAsString());

        // Verify if saveJsonState was called
        //Mockito.verify(gridStateHandlerSpy, Mockito.times(2)).saveJsonState();
    }
} */
