package pixelart.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.scene.canvas.Canvas;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


class GridTest {
    private Grid grid;
    private static int DEFAULT_GRID_WIDTH = 5;
    private static int DEFAULT_GRID_HEIGHT = 5;
    private static int pixelSize = 10;

    @Mock
    private GridStateHandler mockGridStateHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        grid = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, pixelSize);
    }


    @Test
    void testJSONGridSize() {
        assertEquals(DEFAULT_GRID_WIDTH, grid.getJsonGrid().length);
        for (int i = 0; i < DEFAULT_GRID_WIDTH; i++) {
            assertEquals(DEFAULT_GRID_HEIGHT, grid.getJsonGrid().length);
        }
    }

    @Test
    void testJSONGridContent() {
        for (int i = 0; i < DEFAULT_GRID_WIDTH; i++) {
            for (int j = 0; j < DEFAULT_GRID_HEIGHT; j++) {
                assertEquals("#FFFFFF", grid.getJsonGrid()[i][j].toString());  // Compare individual elements, not the whole row
            }
        }
    }

    @Test
    void testCanvasProperties() {
        for (int i = 0; i < DEFAULT_GRID_HEIGHT; i++) {  // Loop through the full grid size
            for (int j = 0; j < DEFAULT_GRID_WIDTH; j++) {
                Canvas canvas = grid.getPixel(i,j).getCanvas();
                assertNotNull(canvas);
                assertEquals(pixelSize, canvas.getWidth());
                assertEquals(pixelSize, canvas.getHeight());
            }
        }
    }

    @Test
    void successfullyRetrievingAllPixels() {
        Pixel[][] retrievedPixels = grid.getAllPixels();
        assertNotNull(retrievedPixels);
        assertEquals(DEFAULT_GRID_HEIGHT, retrievedPixels.length);
        assertEquals(DEFAULT_GRID_WIDTH, retrievedPixels[0].length);

        for (int row = 0; row < DEFAULT_GRID_HEIGHT; row++) {
            for (int col = 0; col < DEFAULT_GRID_WIDTH; col++) {
                assertNotNull(retrievedPixels[row][col]);
            }
        }
    }

    @Test
    void successfullyUpdatingPixels() {
        String hexColor = "#000000";

        for (int row = 0; row < DEFAULT_GRID_HEIGHT; row++) {
            for (int col = 0; col < DEFAULT_GRID_WIDTH; col++) {
                grid.updatePixel(row, col, hexColor);
                assertEquals(grid.getPixel(row, col).getCurrentColor(), hexColor);
            }
        }
    }

    @Test
    void successfullyInitializingGridFromCurrentStateTest() throws IOException, InterruptedException {
        String[][] mockApiState = {
                {"#FF0000", "00FF00", "#0000FF", "#FFFFFF", "#000000"},
                {"#FF0000", "00FF00", "#0000FF", "#FFFFFF", "#000000"},
                {"#FF0000", "00FF00", "#0000FF", "#FFFFFF", "#000000"},
                {"#FF0000", "00FF00", "#0000FF", "#FFFFFF", "#000000"},
                {"#FF0000", "00FF00", "#0000FF", "#FFFFFF", "#000000"}
        };

        HttpClient mockedClient = mock(HttpClient.class);
        @SuppressWarnings("unchecked")
        HttpResponse<String> mockedResponse = (HttpResponse<String>) mock(HttpResponse.class);

        String jsonResponse = new ObjectMapper().writeValueAsString(mockApiState);
        when(mockedResponse.statusCode()).thenReturn(200);

        when(mockedResponse.body()).thenReturn(jsonResponse);

        when(mockedClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers
                .ofString())))
                .thenReturn(mockedResponse);

        GridStateHandler mockHandler = new GridStateHandler();
        mockHandler.setHttpClient(mockedClient);

        Grid gridFromMockState = new Grid(mockApiState, pixelSize);

        gridFromMockState.initializeGridFromState(mockApiState, pixelSize);

        assertNotNull(gridFromMockState.getAllPixels());

        String[][] currentActualState = gridFromMockState.getJsonGrid();
        for (int i = 0; i < mockApiState.length; i++) {
            for (int j = 0; j < mockApiState[0].length; j++) {
                assertEquals(mockApiState[i][j], currentActualState[i][j]);
            }
        }
    }
}


