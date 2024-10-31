
package pixelart.core;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GridStateHandler {

    /**
     * The path to the JSON file where the gris state is saved.
     */
    private final String filepathJson;

    /**
     * The grid instance that this handler is in controll of.
     */
    private final Grid grid;

    /**
     * A 2D array representing the grid's state in a JSON format.
     */
    private JSONArray[][] jsonGrid;

    /**
     * Constructs a GridStateHandler that handles the grid's JSON state.
     *
     * @param fullGrid is the grid instance that this class handles.
     */
    public GridStateHandler(final Grid fullGrid) {
        this.grid = fullGrid;
        this.filepathJson =  "jsonCanvas.json"; // Default JSON file path.
        this.jsonGrid = new JSONArray[grid.getGridSize()][grid.getGridSize()];
    }

    /**
     * Loads the saved grid state from a JSON file, updating the
     * color of each pixel according to the saved state. If the
     * file does not exist or is empty, the grid defaults to an
     * initialized blank/white grid.
     *
     * @throws FileNotFoundException if the specified JSON file does not exist.
     * @throws IOException if an error occurs while reading the file.
     */
    public void loadState() {
        try {
            String jsonContent = readJsonFile(filepathJson);
            if (jsonContent.isEmpty()) {
                // If file empty, defaultes to white grid (already initialized).
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
                    gc.setFill(pixelColor.equals("B")
                    ? Color.BLACK
                    : Color.WHITE);
                    int pixelSize = grid.getPixel(row, col).getPixelSize();
                    gc.fillRect(0, 0, pixelSize, pixelSize);
                }
            }
        } catch (FileNotFoundException e) {
            // file doesn't exist, use defualt white grid (already initialized).
            System.out.println("No existing save file was found."
                                + " \n Initialize a blank canvas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current grid state to the JSON file.
     * This method creates a JSON representation of the state
     * of the grid and writes it to the JSON file.
     *
     * @throws IOExeption if an error happens while write to file.
     */
    public void saveJsonState() {
        JSONObject jsonObjBuilder = new JSONObject();
        jsonObjBuilder.put("canvas", jsonGrid);
        try (BufferedWriter writer =
        new BufferedWriter(new FileWriter(filepathJson))) {
            writer.write(jsonObjBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the content of the JSON file into a string.
     *
     * @param filePathJson the path to the JSON file that's being read.
     * @return content of the file as a String.
     * @throws IOException if an error occurs while reading the file
     * or if file not found.
     */
    public String readJsonFile(final String filePathJson) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader =
        new BufferedReader(new FileReader(filePathJson))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + filePathJson);
        }
        return content.toString();
    }
}
