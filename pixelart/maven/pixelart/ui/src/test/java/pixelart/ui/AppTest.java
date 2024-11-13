package pixelart.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static pixelart.ui.App.SCENE_HEIGHT;
import static pixelart.ui.App.SCENE_WIDTH;

public class AppTest extends ApplicationTest {
    Scene scene;
    @BeforeEach
    public void setup() throws Exception{
        ApplicationTest.launch(App.class);
    }

    /**
     * Tests correct launching of the application
     * */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
        Parent root = loader.load();
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Tests the width and height of the scene

     * Ensures that the height and width corresponds
     * to the expected values set.
     */
    @Test
    void widthAndHeight(){
        Assertions.assertEquals(SCENE_WIDTH,scene.getWidth());
        Assertions.assertEquals(SCENE_HEIGHT,scene.getHeight());
    }

}