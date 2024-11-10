package pixelart.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image; // To change taskbar logo of the app
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The App class is the starter for the JavaFX application Pixelart
 * It sets up the FXML layout, sets up the scene, and displays the pixelart.
 * The application is for making pixel art.
 */
public class App extends Application {

    /** The width of the application scene in pixels. */
    private static final int SCENE_WIDTH = 800;

    /** The height of the application scene in pixels. */
    private static final int SCENE_HEIGHT = 500;

    /**
     * Initializes and displays the main application window for PixelArt.
     *
     * @param primaryStage the primary stage for this application, where
     *                     the application scene is set.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @Override
    public void start(final Stage primaryStage) throws IOException {
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
}
