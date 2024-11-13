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

    /**
     * Instance of Grid class to for testing
     */
    private Grid grid;

    /**
     * Default width of the grid used for testing.
     * Initializing a smaller grid for efficient tests
     */
    private static int DEFAULT_GRID_WIDTH = 4;

    /**
     * Default height of the grid used for testing.
     * Initializing a smaller grid for efficient tests
     */
    private static int DEFAULT_GRID_HEIGHT = 5;

    /**
     * The pixelsize used for testing
     */
    private static int pixelSize = 10;

    /**
     * A mocked instance of a GridStateHandler used for testing
     */
    @Mock
    private GridStateHandler mockGridStateHandler;

    /**
     * Sets up the test environment before each test case.

     * Initializes Mockito annotations for mock creation,
     * and initializes a Grid with the default values.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        grid = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, pixelSize);
    }

    /**
     * Tests that the Grid's dimensions equals to the expected size

     * This test verifies that the height of the JSON grid matches
     * DEFAULT_GRID_HEIGHT and that each row within the grid has a width equal
     * to DEFAULT_GRID_WIDTH.
     */
    @Test
    void testJSONGridSize() {
        assertEquals(DEFAULT_GRID_HEIGHT, grid.getJsonGrid().length);
        for (int i = 0; i < DEFAULT_GRID_HEIGHT; i++) {
            assertEquals(DEFAULT_GRID_WIDTH, grid.getJsonGrid()[i].length);
        }
    }

    /**
     * Verifies that each cell in the JSON grid is initialized to the expected color.

     * This test checks that every cell in the grid is set to #FFFFFF, ensuring
     * the grid is fully initialized with the default color.
     */
    @Test
    void testJSONGridContent() {
        for (int i = 0; i < DEFAULT_GRID_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_GRID_WIDTH; j++) {
                assertEquals("#FFFFFF", grid.getJsonGrid()[i][j].toString());  // Compare individual elements, not the whole row
            }
        }
    }

    /**
     * Verifies the initialization properties on each pixel in the grid

     * This test asserts that the pixel has been initialized,
     * and been initialized with the correct properties */
    @Test
    void testCanvasProperties() {
        for (int i = 0; i < DEFAULT_GRID_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_GRID_WIDTH; j++) {
                Canvas canvas = grid.getPixel(i,j).getCanvas();
                assertNotNull(canvas);
                assertEquals(pixelSize, canvas.getWidth());
                assertEquals(pixelSize, canvas.getHeight());
            }
        }
    }

    /**
     * Verifies the successful retrieval of all pixels in the canvas

     * This test verifies that the method {@link Grid#getAllPixels}
     * returns a non-null 2D array of pixels that matches the default grid size.
     * In addition, it checks that each pixel is non-null, that is, being initialized.
     */
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

    /**
     * Tests a successful update of the color of each pixel in the grid

     * This test updates the color of each pixel in the grid with {@link Grid#updatePixel},
     * as well as verifying the pixel´s current color matches the updated value.
     */
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

    /**
     * Testing a successful retrieval of the width of the Grid
     */
    @Test
    void successfullyRetrievingWidth(){
        assertEquals(DEFAULT_GRID_WIDTH, grid.getGridSizeWidth());
    }

    /**
     * Testing a successful retrieval of the height of the Grid
     */
    @Test
    void successfullyRetrievingHeight(){
        assertEquals(DEFAULT_GRID_HEIGHT, grid.getGridSizeHeight());
    }

    /**
     * Tests initialization of Grid from a simulated API state

     * This test verifies that {@link Grid#initializeGridFromState}
     * correctly initializes a Grid based on a mocked API response.
     *
     * @throws IOException if a JSON processing error occurs
     * @throws InterruptedException if the HTTP request is interrupted
     */
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

    /**
     * Verifies that a RuntimeException is thrown with malformed data in the response
     *
     * This tests provides a Grid containing null to simulate malformed data for the 2D array.
     * The test then verifies that the initializeGridFromState correctly throws a RuntimeException.
     */
    @Test
    void correctlyThrowingRunTimeExceptionTest() throws IOException, InterruptedException {
        String[][] malformedState = {
                {"#FF0000", "#00FF00"},
                null,
                {"#0000FF", "#FFFFFF"}
        };

        Grid testGrid = new Grid(malformedState, pixelSize);

        assertThrows(RuntimeException.class, () ->
                testGrid.initializeGridFromState(malformedState, pixelSize)
        );
    }
}
