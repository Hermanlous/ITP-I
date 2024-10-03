package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppControllerTest {
    private AppController appController;

    @BeforeEach
    public void setup(){
        appController = new AppController();
    }

    @Test
    public void startedDrawingTest(){
        appController.startedDrawing("Test-state for testing");
        String filepath = "checkboxState.txt";
        File file = new File(filepath);
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String fileContent = reader.readLine();
            assertEquals("Test-state for testing", fileContent);
        }catch(FileNotFoundException e){ //Handling the specific FileNotFoundException
            e.printStackTrace();
        }catch(IOException ex){ //Handling other IO exceptions
            ex.printStackTrace();
        }
    }



}
