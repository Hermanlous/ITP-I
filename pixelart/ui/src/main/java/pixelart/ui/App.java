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
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, 400, 450);

        primaryStage.setTitle("PixelArt");
        primaryStage.setScene(scene);
        primaryStage.show();
        Image taskbarLogo = new Image(getClass().getResourceAsStream("/pixelart/ui/taskbarLogo.png"));  // To change taskbar logo
        primaryStage.getIcons().add(taskbarLogo);
        
    }
}
