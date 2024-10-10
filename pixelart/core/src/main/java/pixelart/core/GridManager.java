package pixelart.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * GridManager handles the making and management of a grid of pixels.
 * It updates individual pixels, saves the grid state to a JSON file, and loads the grid state from a JSON file.
 * The grid consists of Canvas elements, where each canvas represents a pixel. Each Canvas is black or white. 
 */

public class GridManager {
    private final int gridSize = 100;
    private final int pixelSize = 10;
    private final Canvas[][] grid = new Canvas[gridSize][gridSize];
    private JSONArray[] jsonGrid = new JSONArray[gridSize];
    private final String filepathJson = "jsonCanvas.json";

    public GridManager() {
        initializeGrid();
    }

    private void initializeGrid() {
        for (int row = 0; row < gridSize; row++) {
            jsonGrid[row] = new JSONArray();  
            for (int col = 0; col < gridSize; col++) {
                Canvas canvas = new Canvas(pixelSize, pixelSize);
                jsonGrid[row].put("W");
                grid[row][col] = canvas;
                
                // Initialize canvas to white
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, pixelSize, pixelSize);
            }
        }
    }

    public Canvas[][] getGrid() {
        return grid;
    }
 
    // Updates the color of a pixel in the grid.
    public void updatePixel(int row, int col, boolean isBlack) { 
        Canvas pixel = grid[row][col];
        GraphicsContext gc = pixel.getGraphicsContext2D();
        gc.setFill(isBlack ? Color.BLACK : Color.WHITE); // If isBlack is true, the pixel is set to black. If isBlack is false, it is set to white.
        gc.fillRect(0, 0, pixelSize, pixelSize);
        jsonGrid[row].put(col, isBlack ? "B" : "W");
        saveJsonState(); // The grid state is updated in the JSON grid and saved.
    }

    public void loadState() {
        try {
            String jsonContent = readJsonFile();
            if (jsonContent.isEmpty()) {
                // If file is empty, it uses the defaulted white grid (already initialized)
                return;
            }

            JSONObject jsonObjReader = new JSONObject(jsonContent);
            JSONArray canvasJson = jsonObjReader.getJSONArray("canvas");

            for (int row = 0; row < gridSize; row++) {

                JSONArray jsonRow = canvasJson.getJSONArray(row);

                for (int col = 0; col < gridSize; col++) {
                    String pixelColor = jsonRow.getString(col);
                    Canvas canvas = grid[row][col];
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setFill(pixelColor.equals("B") ? Color.BLACK : Color.WHITE);
                    gc.fillRect(0, 0, pixelSize, pixelSize);
                    jsonGrid[row].put(col, pixelColor);
                }
            }
        } catch (FileNotFoundException e) {
            // file doesn't exist, use defualt white grid (already initialized)
            System.out.println("No existing save file found. Initialize a blank canvas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveJsonState() { // Saves the current grid state to the JSON file.
        JSONObject jsonObjBuilder = new JSONObject();
        jsonObjBuilder.put("canvas", jsonGrid);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathJson))) {
            writer.write(jsonObjBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readJsonFile() throws IOException { // Reads the content of the JSON file into a string.
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepathJson))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
}