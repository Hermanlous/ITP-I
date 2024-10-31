package pixelart.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image; // To change taskbar logo of the app
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The App class is the starter for the JavaFX application Pixelart
 * It sets up the FXML layout, sets up the scene, and displays the pixelart.
 * The application is for making pixel art.
 */
public class App extends Application {

    /**
     * The width of the application scene in pixels.
     */
    private static final int SCENE_WIDTH = 400;

    /**
     * The height of the application scene in pixels.
     */
    private static final int SCENE_HEIGHT = 450;

    /**
     * Initializes and displays the main application window for PixelArt.
     *
     * @param primaryStage the primary stage for this application, where
     *                     the application scene is set.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @Override
    public void start(final Stage primaryStage) throws IOException {
        FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("PixelArt");
        primaryStage.setScene(scene);
        primaryStage.show();
        Image taskbarLogo = new
        Image(getClass().getResourceAsStream("/pixelart/ui/taskbarLogo.png"));
        primaryStage.getIcons().add(taskbarLogo);
    }
}
