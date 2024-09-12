package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file from /resources/app/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/App.fxml"));
        AnchorPane root = loader.load();

        // Create the scene with the FXML layout
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("Group 52 - 2024");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
