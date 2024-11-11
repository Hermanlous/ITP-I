package pixelart.ui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorControllerTest {
    private ColorController colorController;
    private HBox colorPalette;
    private String[] colors;

    @BeforeEach
    void setUp() {
        try {
            Platform.startup(() -> {
            });
        } catch (IllegalStateException e) {
        }
        colorPalette = new HBox();
        colorController = new ColorController(colorPalette);
        colors = new String[]{"#000000",
                "#FFFFFF",
                "#ff0000",
                "#00ff00",
                "#0000ff",
                "#ffff00",
                "#ff00ff",
                "#00ffff",};
    }

    @Test
    void testInitialize() {
        assertNotNull(colorController);
    }

    @Test
    void testInitializeColorPalette() {
        colorController.initializeColorPalette();
        assertEquals(colorPalette.getChildren().size(), colors.length);
    }

    @Test
    void setNumberOfButtons() {
        Platform.runLater(() -> {
            colorController.initializeColorPalette();
            assertEquals(8, colorPalette.getChildren().size());
        });
    }

    @Test
    void setCorrectColorToButton() {
        Platform.runLater(() -> {
            colorController.initializeColorPalette();
            Button button = (Button) colorPalette.getChildren().get(0);

            String style = button.getStyle();
            assertTrue(style.contains("-fx-background-color: #000000"));
            assertEquals("#000000", button.getUserData());
        });
    }
}