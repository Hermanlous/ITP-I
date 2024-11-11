package pixelart.ui;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import pixelart.core.Grid;

public class AppControllerTest extends ApplicationTest {

    private AppController controller;
    private static final String YELLOW = "#ffff00";
    private static final String BLACK = "#000000";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Reset to initial state if needed
        clickOn(".button"); // Click first color (black) to ensure we start with a known state
    }

    @Test
    public void testDrawSmileyFace() {
        clickOn(".button:nth-child(6)");

        drawCircle(30, 20, 8);

        // Switch to black color for features
        clickOn(".button:nth-child(1)"); // Black color

        // Draw eyes
        // Left eye
        clickPixel(26, 17);
        clickPixel(27, 17);

        // Right eye
        clickPixel(33, 17);
        clickPixel(34, 17);

        // Draw smile
        drawSmile(30, 23, 5);

        // Verify the drawing
        verifyFaceElements();
    }

    private void drawCircle(int centerX, int centerY, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (Math.sqrt(x*x + y*y) <= radius) {
                    clickPixel(centerX + x, centerY + y);
                }
            }
        }
    }

    private void drawSmile(int centerX, int centerY, int width) {
        for (int x = -width; x <= width; x++) {
            // Using a parabola for the smile: y = ax² where 'a' determines the curvature
            double a = 0.1;
            int y = (int)(a * x * x);
            clickPixel(centerX + x, centerY + y);
        }
    }

    private void clickPixel(int x, int y) {
        if (x >= 0 && x < GridController.GRID_SIZE_WIDTH &&
                y >= 0 && y < GridController.GRID_SIZE_HEIGHT) {

            // Convert grid coordinates to pixel coordinates
            double pixelSize = GridController.PIXEL_SIZE;
            moveTo("#gridPane")
                    .moveBy(x * pixelSize + pixelSize/2, y * pixelSize + pixelSize/2)
                    .clickOn(MouseButton.PRIMARY);

            // Add small delay to ensure click is registered
            sleep(50);
        }
    }

    private void verifyFaceElements() {
        Grid grid = controller.getGridController().grid;

        // Verify yellow face outline
        verifyPixelColor(30, 20, YELLOW); // Top of face
        verifyPixelColor(30, 28, YELLOW); // Bottom of face
        verifyPixelColor(22, 20, YELLOW); // Left side
        verifyPixelColor(38, 20, YELLOW); // Right side

        // Verify black eyes
        verifyPixelColor(26, 17, BLACK); // Left eye
        verifyPixelColor(27, 17, BLACK);
        verifyPixelColor(33, 17, BLACK); // Right eye
        verifyPixelColor(34, 17, BLACK);

        // Verify black smile
        verifyPixelColor(30, 23, BLACK); // Center of smile
        verifyPixelColor(27, 24, BLACK); // Left part of smile
        verifyPixelColor(33, 24, BLACK); // Right part of smile
    }

    private void verifyPixelColor(int x, int y, String expectedColor) {
        String actualColor = controller.getGridController().grid.getPixel(y, x).getCurrentColor();
        assertEquals(expectedColor.toLowerCase(),
                actualColor.toLowerCase(),
                String.format("Pixel at (%d,%d) should be %s but was %s",
                        x, y, expectedColor, actualColor));
    }

    @Test
    public void testColorSelection() {
        // Test that color selection works
        clickOn(".button:nth-child(6)"); // Yellow
        assertEquals(YELLOW.toLowerCase(),
                controller.getGridController().getCurrentColor().toLowerCase(),
                "Color should be yellow after selecting yellow button");

        clickOn(".button:nth-child(1)"); // Black
        assertEquals(BLACK.toLowerCase(),
                controller.getGridController().getCurrentColor().toLowerCase(),
                "Color should be black after selecting black button");
    }

    @Test
    public void testEraser() {
        // Draw something
        clickPixel(1, 1);

        // Erase it with right click
        moveTo("#gridPane")
                .moveBy(GridController.PIXEL_SIZE * 1.5, GridController.PIXEL_SIZE * 1.5)
                .clickOn(MouseButton.SECONDARY);

        // Verify pixel is white (erased)
        verifyPixelColor(1, 1, "#FFFFFF");
    }
}