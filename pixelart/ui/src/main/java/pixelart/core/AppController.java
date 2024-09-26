package pixelart.core;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

/**
 * AppController is the main controller class for the JavaFX application tbdApp.
 * It controls the interaction between the user and the 100x100 (may change) grid of canvases.
 * The users can draw or erase on the different pixels  using left and right mouse clicks. More to come.
 * Which pixels that are coloured in are saved in a text file.
 * 1: Chatgpt prompt: "Can you help me with GraphicsContext and canvas correlation" downloaded 13.09
 * 2: ChatGpt prompt "code snippet + Can you check for errors?"
 * 3: ChatGpt prompt "code snippet +  I feel like I am creating some problems for myself, do you have any recomendations to make the json file be persistent with my pixel click?"
 */

public class AppController {


    @FXML
    private GridPane gridPane;
    @FXML
    private Canvas[][] MainCanvas = new Canvas[100][100];//Size may change

    private JSONArray[] jsonGrid = new JSONArray[100];

    private final int pixelSize = 10;

    private String filepathJson = "jsonCanvas.json";
    private String filepathDrawfile = "drawState.txt";

    //sources
    // 1: ChatGPT "How would one initialize a Canvas in a GridPane"
    @FXML
    public void initialize() throws FileNotFoundException {
        for (int r = 0; r < 100; r++) {
            jsonGrid[r] = new JSONArray();
            for (int c = 0; c < 100; c++) {
                Canvas canvas = new Canvas(pixelSize, pixelSize); //1
                jsonGrid[r].put("W");
                gridPane.add(canvas, r, c);
                MainCanvas[r][c] = canvas; //1
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    try {
                        pixelClick(row, column, event);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        jsonReader();
    }

    /*This function aims to draw and erase colours from the canvas. In order to erase, the user should right-click on a pixel
    and in order to draw, the user should left-click on a pixel */
    private void pixelClick(int i, int j, MouseEvent event) throws FileNotFoundException {
        Canvas pixel = MainCanvas[i][j];
        GraphicsContext gc = pixel.getGraphicsContext2D();//1
        jsonBuilder();
        if(event.getButton() == MouseButton.SECONDARY){
            System.out.println(event.getButton());
            gc.setFill(Color.WHITE);
            startedDrawing("Ereased!");
            gc.fillRect(0, 0, pixelSize,pixelSize);
            jsonGrid[i].put(j, "W");
        }
        else {
            gc.setFill(Color.BLACK);
            startedDrawing("Wow! You can draw!");
            gc.fillRect(0, 0, pixelSize,pixelSize);
            jsonGrid[i].put(j, "B");
        }

    }


    public void startedDrawing(String state) throws FileNotFoundException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepathDrawfile));
            writer.write(state);
            writer.close();
        }catch(IOException e){
            throw new FileNotFoundException();
        }
    }

    public void jsonBuilder() throws FileNotFoundException {
        JSONObject jsonObjBuilder = new JSONObject();
        jsonObjBuilder.put("canvas", jsonGrid);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepathJson));
            writer.write(jsonObjBuilder.toString());
            writer.close();
        } catch (IOException e){
            throw new FileNotFoundException();
        }
    }


    public void jsonReader() throws FileNotFoundException {

        try {
            FileReader reader = new FileReader(filepathJson);
            StringBuilder sb = new StringBuilder();
            int i;
            while((i = reader.read()) != -1){
                sb.append((char) i);
            }
            reader.close();

            JSONObject jsonObjReader = new JSONObject(sb.toString());
            JSONArray canvasJson = jsonObjReader.getJSONArray("canvas");

            for (int r = 0; r < 100; r++) {
                JSONArray jsonRow = canvasJson.getJSONArray(r);
                for (int c = 0; c < 100; c++) {
                    String pixelColor = jsonRow.get(c).toString();
                    Canvas canvas = MainCanvas[r][c];
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    if(pixelColor.equals("B") ){
                        gc.setFill(Color.BLACK);
                    }
                    else if(pixelColor.equals("W")){
                        gc.setFill(Color.WHITE);
                    }
                    gc.fillRect(0, 0, pixelSize, pixelSize);
                }
            }
        }
        catch (IOException e) {
            throw new FileNotFoundException();
        }
    }//To here
}
