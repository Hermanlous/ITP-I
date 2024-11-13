package pixelart.ui;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

import java.io.IOException;

public class GridControllerTest {

    /**
     * The grid used for testing
     */
    @Mock
    private Grid grid;

    /**
     * The GridPane used to for testing
     */
    @Mock
    private GridPane gridPane;

    /**
     * The GridStateHandler used for testing
     */
    @Mock
    private GridStateHandler gridStateHandler;

    /**
     * The GridController used to test GridController
     */
    private GridController gridController;

    /**
     * Sets up the testing environment before each test case

     * Initializing Mock annotation as well as a new GridController
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gridController = new GridController(grid, gridPane, gridStateHandler);
    }

    /**
     * Tests that #initializeGridPane correctly populates gridPane

     * The tests mock a grid and checks that gridPane receives
     * the correct amount of calls to add Pixel elements.
     */
    @Test
    void testInitializeGridPane() {
        Pixel[][] pixels = createMockGrid();
        when(grid.getAllPixels()).thenReturn(pixels);
        gridController.initializeGridPane();

        int timesInvoked = GridController.GRID_SIZE_HEIGHT * GridController.GRID_SIZE_WIDTH;
        verify(gridPane, times(timesInvoked));
    }

    /**
     * Tests that updating currentColor applies correctly

     * Ensuring that the set color matches the currentColor
     * in the controller.
     */
    @Test
    void colorManagementTest() {
        String testColor = "#FF0000";

        gridController.setCurrentColor(testColor);
        assertEquals(testColor, gridController.getCurrentColor());
    }

    /**
     * Tests that saving to server and updating color

     * This test mocks a click on a Pixel and verifies
     * that the grid state is saved in the server and
     * that the Pixel´s color is updated.
     *
     * @throws IOException if an IOException occurs
     * @throws InterruptedException if the instruction is interrupted
     */
    @Test
    void saveToServerTest() throws IOException, InterruptedException {
        String[][] mockGrid = new String[GridController.GRID_SIZE_HEIGHT][GridController.GRID_SIZE_WIDTH];
        when(grid.getJsonGrid()).thenReturn(mockGrid);

        Pixel pixel = mock(Pixel.class);
        when(grid.getPixel(0, 0)).thenReturn(pixel);

        MouseEvent mockMouseEvent = mock(MouseEvent.class);
        when(mockMouseEvent.getButton()).thenReturn(MouseButton.PRIMARY);

        gridController.setCurrentColor("#000000");
        gridController.pixelClick(0, 0, mockMouseEvent);

        verify(gridStateHandler).postCanvas(mockGrid);
        verify(pixel).updateColor("#000000");
    }

    /**
     * Creates a mock 2D array of `Pixel` objects for testing.

     * Every Pixel object is mocked with a Canvas, allowing GridController
     * methods to interact with a simulated Grid.
     *
     * @return a mock grid of Pixel objects
     */
    private Pixel[][] createMockGrid() {
        Pixel[][] pixels = new Pixel[GridController.GRID_SIZE_HEIGHT][GridController.GRID_SIZE_WIDTH];
        for (int r = 0; r < GridController.GRID_SIZE_HEIGHT; r++) {
            for (int c = 0; c < GridController.GRID_SIZE_WIDTH; c++) {
                Pixel mockPixel = mock(Pixel.class);
                when(mockPixel.getCanvas()).thenReturn(mock(Canvas.class));
                pixels[r][c] = mockPixel;
            }
        }
        return pixels;
    }
}
