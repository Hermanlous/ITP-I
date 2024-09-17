package app;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;

public class AppController {

    @FXML
    private GridPane gridPane;
    @FXML
    private Canvas[][] MainCanvas = new Canvas[100][100];

    private final int pixelSize = 10;//Not less than this, it's too small to be visually pleasing.

    @FXML
    public void initialize() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Canvas canvas = new Canvas(pixelSize, pixelSize); //ChatGPT "How would one initialize a Canvas in a GridPane" from here
                //Initialize grid in file here.
                gridPane.add(canvas, i, j);
                MainCanvas[i][j] = canvas; //to here
                int r = i;
                int c = j;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> pixelClick(r, c, event)); //Calls pixelClick

            }
        }
    }

    private void pixelClick(int i, int j, MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY){//Used stackOverflow: https://stackoverflow.com/questions/1515547/right-click-in-javafx downloaded 13.09
            System.out.println(event.getButton()); //Had issues with right mouseclick, "sout" for log.
            Canvas canvas = MainCanvas[i][j];
            GraphicsContext gc = canvas.getGraphicsContext2D(); //ChatGPT "How does GraphicsContext work?"
            gc.setFill(Color.WHITE);
            startedDrawing("Ereased!");//Changes the state, when using the right mousebutton. Should instead update filegrid.
            System.out.println("ereased?");
            gc.fillRect(0, 0, pixelSize,pixelSize);
        }else{
            Canvas canvas = MainCanvas[i][j];
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.BLACK);
            startedDrawing("Wow! You can draw!"); //Changes the state, when using the left mousebutton. Should instead update filegrid.
            System.out.println("wow, you can draw?");
            gc.fillRect(0, 0, pixelSize,pixelSize);
        }

    }

    //private void pixelImage(Canvas canvas, int i, int j) { //Write your image to another file.
    //    Canvas imageCanvas = MainCanvas[i][j];
    //}







    private void startedDrawing(String state){
        // Need to build a filepath to txt, now it's just hanging in src...
        String filepath = "checkboxState.txt";
        try(FileWriter writer = new FileWriter(filepath)){
            writer.write(state);
        }catch(IOException e){
            e.printStackTrace();
            //Forgot how to append more text and save state.
        }
    }
}
