package pixelart.core;

import javafx.scene.canvas.GraphicsContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteJsonTest {
    private GridManager gridManager;
    private static int gridSize = 5;
    private static int pixelSize = 10;

    @BeforeEach
    public void setUp(){
        this.gridManager = new GridManager(gridSize,pixelSize);
    }

    @Test
    public void testSaveJsonState() throws Exception {
        JSONArray[] testJsonGrid = new JSONArray[gridSize];
        for (int i = 0; i < gridSize; i++) {
            testJsonGrid[i] = new JSONArray();
            for (int j = 0; j < gridSize; j++) {
                testJsonGrid[i].put("B");
            }
        }
        Field jsonGridField = GridManager.class.getDeclaredField("jsonGrid");
        jsonGridField.setAccessible(true);
        jsonGridField.set(gridManager, testJsonGrid);

        Field filepathField = GridManager.class.getDeclaredField("filepathJson");
        filepathField.setAccessible(true);
        filepathField.set(gridManager, "testJsonCanvas.json");

        gridManager.saveJsonState();
        File file = new File("testJsonCanvas.json");
        assertTrue(file.exists(), "Output file should exist");

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        }
        /* Line 90 - 94: ChatGPT: How can I ensure that the JSON is correctly compared?*/
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("canvas", testJsonGrid);
        JSONObject actualJson = new JSONObject(fileContent.toString());
        assertEquals(expectedJson.toString(), actualJson.toString());
    }

    @Test
    public void testUpdatePixel() {
        GridManager gridManagerSpy = Mockito.spy(gridManager);
        int row = 3;
        int col = 3;
        boolean isBlack = true;
        gridManagerSpy.updatePixel(row, col, isBlack);
        assertEquals("B", gridManagerSpy.jsonGrid[row].getString(col));
        isBlack = false;
        gridManagerSpy.updatePixel(row, col, isBlack);
        assertEquals("W", gridManagerSpy.jsonGrid[row].getString(col));

        /*ChatGPT: How can I ensure that saveJsonState is called?*/
        Mockito.verify(gridManagerSpy, Mockito.times(2)).saveJsonState();
    }

}
