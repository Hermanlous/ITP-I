package pixelart.core;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.*;

public class GridStateHandler {
    private final String filepathJson;
    private final Grid grid;
    private JSONArray[][] jsonGrid;

    public GridStateHandler (Grid grid){
        this.grid = grid;
        this.filepathJson =  "jsonCanvas.json";
        this.jsonGrid = new JSONArray[grid.getGridSize()][grid.getGridSize()];
    }

    public void loadState() {
        try {
            String jsonContent = readJsonFile(filepathJson);
            if (jsonContent.isEmpty()) {
                // If file is empty, it uses the defaulted white grid (already initialized)
                return;
            }

            JSONObject jsonObjReader = new JSONObject(jsonContent);
            JSONArray canvasJson = jsonObjReader.getJSONArray("canvas");

            for (int row = 0; row < grid.getGridSize(); row++) {

                JSONArray jsonRow = canvasJson.getJSONArray(row);

                for (int col = 0; col < grid.getGridSize(); col++) {
                    String pixelColor = jsonRow.getString(col);
                    Canvas canvas = grid.getPixel(row, col).getCanvas();
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setFill(pixelColor.equals("B") ? Color.BLACK : Color.WHITE);
                    int pixelSize = grid.getPixel(row, col).getPixelSize();
                    gc.fillRect(0, 0, pixelSize, pixelSize);
                }
            }
        } catch (FileNotFoundException e) {
            // file doesn't exist, use defualt white grid (already initialized)
            System.out.println("No existing save file found. Initialize a blank canvas.");
        } catch (IOException e) {
            e.printStackTrace();
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
