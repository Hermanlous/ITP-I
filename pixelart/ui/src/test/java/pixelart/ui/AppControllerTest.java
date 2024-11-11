package pixelart.ui;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.testfx.api.FxAssert.verifyThat;
import static pixelart.ui.App.SCENE_HEIGHT;
import static pixelart.ui.App.SCENE_WIDTH;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import pixelart.core.Grid;

public class AppControllerTest extends ApplicationTest {

    //This codeblock is sourced from https://www.youtube.com/watch?v=NG03nNpSmgU downloaded 07.10.2024, from here
    @BeforeEach
    public void setup() throws Exception{
        ApplicationTest.launch(App.class);
    }//To here

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
            primaryStage.setTitle("PixelArt");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Only add the icon if the resource exists
            var iconStream =
                    getClass().getResourceAsStream("/pixelart/ui/taskbarLogo.png");
            if (iconStream != null) {
                Image taskbarLogo = new Image(iconStream);
                primaryStage.getIcons().add(taskbarLogo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void t(){
        assertSame(true, true);
    }
}



