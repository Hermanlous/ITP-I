package pixelart.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppControllerTest extends ApplicationTest {
    @BeforeEach
    public void setup() throws Exception{
        ApplicationTest.launch(App.class);
    }
    
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
        }
    }
}
