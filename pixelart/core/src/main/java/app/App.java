package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The App class is the starter for the JavaFX application tdbApp
 * It sets up the FXML layout, sets up the scene, and displays the app.
 * The application is for making pixel art.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/App.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, 400, 450);
        primaryStage.setTitle("PixelArt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);
    }
}
