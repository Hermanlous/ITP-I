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

/**
 * Test class for our GridController, that is responsible for the Grid
 */
public class GridControllerTest {

    /**
     * The grid used to test GridController
     * */
    @Mock
    private Grid grid;

    /**
     * The GridPane used to test GridController
     * */
    @Mock
    private GridPane gridPane;


    @Mock
    private GridStateHandler gridStateHandler;


    private GridController gridController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gridController = new GridController(grid, gridPane, gridStateHandler);
    }

    /**
     * Test to initialize GridPane
     * */
    @Test
    void testInitializeGridPane() {
        Pixel[][] pixels = createMockGrid();
        when(grid.getAllPixels()).thenReturn(pixels);
        gridController.initializeGridPane();

        int timesInvoked = GridController.GRID_SIZE_HEIGHT * GridController.GRID_SIZE_WIDTH;
        verify(gridPane, times(timesInvoked));
    }

    @Test
    void colorManagementTest() {
        String testColor = "#FF0000";

        gridController.setCurrentColor(testColor);
        assertEquals(testColor, gridController.getCurrentColor());
    }

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
