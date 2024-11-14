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

    /**
     * AppController used for testing
     */
    private AppController appController;

    /**
     * GridStateHandler used for testing
     */
    private GridStateHandler gridStateHandler;

    /**
     * HttpClient used for testing
     */
    private HttpClient mockHttpClient;

    /**
     * ObjectMapper used for testing
     */
    private ObjectMapper mockObjectMapper;

    /**
     * Initializes the application stage with a mock HTTP client and object mapper.

     * This method sets up the stage, loads the main application FXML, and
     * initializes appController and gridStateHandler with mock dependencies.
     * Mocks an HTTP response with status 200 and a body of "Success!" to
     * simulate server interaction.
     *
     * @param stage the primary stage for the application
     * @throws Exception if there is an error loading the FXML or initializing
     * dependencies.
     */
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

    /**
     * Simulates clicks on specific canvas elements in the grid and verifies
     * that each click triggers an HTTP request through the mocked HttpClient.
     * It draws a moustache :D
     *
     * @throws Exception if an HTTP request or sleep operation fails
     */
    @Test
    public void testClickOnCanvas() throws Exception {

        int[] moustache = {919, 979, 980, 1040, 981, 1041, 922, 982, 1042, 1102, 863, 923, 983, 1043, 1103, 804, 864, 924, 984, 1044, 1104, 805, 865, 925, 985, 1045, 1105, 746, 806, 866, 926, 986, 1046, 1106, 747, 807, 867, 927, 987, 1047, 808, 868, 928, 988, 1048, 809, 869, 929, 989, 870, 930, 811, 871, 931, 991, 812, 872, 932, 992, 1052, 753, 813, 873, 933, 993, 1053, 754, 814, 874, 934, 994, 1054, 1114, 815, 875, 935, 995, 1055, 1115, 816, 876, 936, 996, 1056, 1116, 877, 937, 997, 1057, 1117, 938, 998, 1058, 1118, 999, 1059, 1000, 1060, 941, 1001};

        sleep(1000);
        //Chatgpt was used here for the lookup query.
        GridPane gridPane = lookup("#gridPane").query();

        for (int index : moustache) {
            clickOn(gridPane.getChildren().get(index));
        }

        verify(mockHttpClient, times(moustache.length)).send(
            any(HttpRequest.class),
            eq(HttpResponse.BodyHandlers.ofString())
        );
    }

    /**
     * Tests the initial color of a randomly selected canvas in the grid.

     * This test randomly selects a canvas element from `gridPane` and verifies
     * that its initial fill color is `Color.WHITE`, to ensure correct
     * initialization of the Grid.
     */
    @Test
    public void initGrid(){
        GridPane gridPane = lookup("#gridPane").query();
        Random rand = new Random();
        int random = rand.nextInt(gridPane.getColumnCount());
        //Chatgpt was used here for Node and casting node as Canvas. Code was generated from here:
        Node node = gridPane.getChildren().get(random);
        Canvas canvas = (Canvas) node;
        //to here.
        Assertions.assertEquals(canvas.getGraphicsContext2D().getFill(), Color.WHITE);
    }

    /**
     * Verifies the number of columns and rows in the `gridPane`.

     * This test checks that the grid has the correct dimensions, ensuring that
     * the dimensions of the GridPane is as expected.
     */
    @Test
    public void testHowManyCanvases(){
        GridPane gridPane = lookup("#gridPane").query();

        Assertions.assertEquals(60, gridPane.getColumnCount());
        Assertions.assertEquals(34, gridPane.getRowCount());
    }
}