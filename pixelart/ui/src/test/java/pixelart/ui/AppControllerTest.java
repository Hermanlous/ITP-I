package pixelart.ui; //TODO all these tests fail... But maven is happy. 
//This happens because these tests use the old gridMAnager(or appcontroller), to make the grid.

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Random;

public class AppControllerTest extends ApplicationTest {
    //This codeblock is sourced from https://www.youtube.com/watch?v=NG03nNpSmgU downloaded 07.10.2024, from here
    @BeforeEach
    public void setup() throws Exception{
        ApplicationTest.launch(App.class);
    }//To here
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, 400, 450);
        primaryStage.setTitle("PixelArt");
        primaryStage.setScene(scene);
        primaryStage.show();
        Image taskbarLogo = new Image(getClass().getResourceAsStream("/pixelart/ui/taskbarLogo.png"));  // To change taskbar logo
        primaryStage.getIcons().add(taskbarLogo);
    }
    @Test
    public void testHowManyCanvases(){
        GridPane gridPane = lookup("#gridPane").query();
        Assertions.assertEquals(gridPane.getColumnCount(), 100);
        Assertions.assertEquals(gridPane.getRowCount(), 100);
    }
    @Test
    public void initGrid(){
        GridPane gridPane = lookup("#gridPane").query();
        Random rand = new Random();
        int random = rand.nextInt(gridPane.getColumnCount());
        Node node = gridPane.getChildren().get(random);
        Canvas canvas = (Canvas) node;
        Assertions.assertEquals(canvas.getGraphicsContext2D().getFill(), Color.WHITE);
    }
    @Test
    public void testClickOnCanvas() {
        
        GridPane gridPane = lookup("#gridPane").query();
        
        sleep(3000);

        // smily
        int[] testPixelIndexes = {4945, 5445, 4747, 4848, 4949, 5049, 5149, 5249, 5349, 5449, 5449, 5548, 5647};

        for (int index : testPixelIndexes) {
            Node node = gridPane.getChildren().get(index); // get pixel

            Canvas canvas = (Canvas) node;

            // draw smily
            clickOn(canvas);
            Assertions.assertEquals(canvas.getGraphicsContext2D().getFill(), Color.BLACK);
        }
    }

    @Test
    public void testClickOnCanvasSecondary() {
        
        GridPane gridPane = lookup("#gridPane").query();
        sleep(3000);

        // smily
        int[] testPixelIndexes = {4945, 5445, 4747, 4848, 4949, 5049, 5149, 5249, 5349, 5449, 5449, 5548, 5647};

        for (int index : testPixelIndexes) {
            Node node = gridPane.getChildren().get(index); // get pixel

            Canvas canvas = (Canvas) node;

            // erease smily
            clickOn(canvas, MouseButton.SECONDARY);
            Assertions.assertEquals(canvas.getGraphicsContext2D().getFill(), Color.WHITE);
        }
    }
}
