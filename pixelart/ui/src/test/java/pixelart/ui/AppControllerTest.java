package pixelart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pixelart.core.GridStateHandler;

import java.io.File;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Random;

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

        mockHttpClient = mock(HttpClient.class);
        mockObjectMapper = spy(new ObjectMapper());

        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("Success!");

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);
        System.out.println(mockResponse.body());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pixelart/ui/App.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();

        appController = loader.getController();
        gridStateHandler = appController.getGridController().getGridStateHandler();
        gridStateHandler.setHttpClient(mockHttpClient);
        gridStateHandler.setObjectMapper(mockObjectMapper);
    }

    @Test
    public void testClickOnCanvas() throws Exception {

        sleep(1000);
        GridPane gridPane = lookup("#gridPane").query();

        clickOn(gridPane.getChildren().get(20));

        verify(mockHttpClient, timeout(5000)).send(
                any(HttpRequest.class),
                eq(HttpResponse.BodyHandlers.ofString())
        );
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
    public void testHowManyCanvases(){
        GridPane gridPane = lookup("#gridPane").query();

        Assertions.assertEquals(60, gridPane.getColumnCount());
        Assertions.assertEquals(34, gridPane.getRowCount());
    }
}