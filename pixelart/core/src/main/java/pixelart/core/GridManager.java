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
    private final int gridSizeHeight;
    private final int gridSizeWidth;
    private final int pixelSize;
    private final Canvas[][] grid;
    JSONArray[] jsonGrid;
    private final String filepathJson;

    public GridManager(int gridSizeHeight, int gridSizeWidth, int pixelSize) {
        this.gridSizeHeight = gridSizeHeight;
        this.gridSizeWidth = gridSizeWidth;
        this.pixelSize = pixelSize;
        this.grid = new Canvas[gridSizeHeight][gridSizeWidth];
        this.jsonGrid = new JSONArray[gridSizeHeight];
        this.filepathJson = "jsonCanvas.json";
        initializeGrid();
    }

    public void initializeGrid() {
        for (int row = 0; row < gridSizeHeight; row++) {
            jsonGrid[row] = new JSONArray();
            for (int col = 0; col < gridSizeWidth; col++) {
                Canvas canvas = new Canvas(pixelSize, pixelSize);
                jsonGrid[row].put(col, "W");
                grid[row][col] = canvas;
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, pixelSize, pixelSize);
            }
        }
    }

    public javafx.scene.canvas.Canvas[][] getGrid() {
        return grid;
    }

    public JSONArray[] getJsonGrid() {
        return jsonGrid;
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

    public void loadState() throws IOException {
        try {
            String jsonContent = readJsonFile(filepathJson);
            if (jsonContent.isEmpty()) {
                // If file is empty, it uses the defaulted white grid (already initialized)
                return;
            }

            JSONObject jsonObjReader = new JSONObject(jsonContent);
            JSONArray canvasJson = jsonObjReader.getJSONArray("canvas");

            for (int row = 0; row < gridSizeHeight; row++) {

                JSONArray jsonRow = canvasJson.getJSONArray(row);

                for (int col = 0; col < gridSizeWidth; col++) {
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
            throw e;  // throw the IOException
        }
    }

    public void saveJsonState() { // Saves the current grid state to the JSON file.
        JSONObject jsonObjBuilder = new JSONObject();
        jsonObjBuilder.put("canvas", jsonGrid);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathJson))) {
            writer.write(jsonObjBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readJsonFile(String filepathJson) throws IOException { // Reads the content of the JSON file into a string.
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepathJson))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + filepathJson);
        }
        return content.toString();
    }
}