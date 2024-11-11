package pixelart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pixelart.core.GridStateHandler;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static pixelart.ui.App.SCENE_HEIGHT;
import static pixelart.ui.App.SCENE_WIDTH;

public class AppControllerTest extends ApplicationTest {
    private AppController appController;
    private GridStateHandler gridStateHandler;
    private HttpClient mockHttpClient;
    private ObjectMapper mockObjectMapper;



    @Override
    public void start(Stage stage) throws Exception {
        // Initialize mocks
        mockHttpClient = mock(HttpClient.class);
        mockObjectMapper = spy(new ObjectMapper());

        // Setup mock response
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("[[\"#FFFFFF\"]]");

        // Setup mock client behavior
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockResponse);

        // Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
        Parent root = loader.load();

        // Set up the scene
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();

        // Get controller and inject mocks
        appController = loader.getController();
        gridStateHandler = appController.getGridController().getGridStateHandler();
        gridStateHandler.setHttpClient(mockHttpClient);
        gridStateHandler.setObjectMapper(mockObjectMapper);
    }

    @Test
    public void testClickOnCanvas() throws Exception {
        // Wait for JavaFX to be ready
        sleep(1000);

        // Get the GridPane
        GridPane gridPane = lookup("#gridPane").query();

        // Ensure gridPane is not null and has children
        if (gridPane == null || gridPane.getChildren().isEmpty()) {
            throw new AssertionError("GridPane not properly initialized");
        }

        // Click on a specific pixel (for example, the first one)
        clickOn(gridPane.getChildren().get(0));

        // Verify HTTP client was called
        verify(mockHttpClient, timeout(5000)).send(
                any(HttpRequest.class),
                eq(HttpResponse.BodyHandlers.ofString())
        );
    }
}