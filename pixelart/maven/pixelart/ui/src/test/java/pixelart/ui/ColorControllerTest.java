package pixelart.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ColorControllerTest {
    private ColorController colorController;
    private HBox colorPalette;
    private String[] colors;

    /**
     * Sets up the testing environment before each test case

     * Initializes colorPalette, colorController and colors for testing
     */
    @BeforeEach
    void setUp() {
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

    /**
     * Verifies that the colorController is correctly
     * initialized
     */
    @Test
    void initializeControllerTest() {
        assertNotNull(colorController);
    }

    /**
     * Verifies that the colorPalette is correctly
     * initialized
     */
    @Test
    void testInitializeColorPaletteTest() {
        colorController.initializeColorPalette();
        assertEquals(colorPalette.getChildren().size(), colors.length);
    }

    /**
     * Verifies that the number of buttons equals
     * the number of colors.
     */
    @Test
    void setNumberOfButtonsTest() {
            colorController.initializeColorPalette();
            assertEquals(8, colorPalette.getChildren().size());
    }

    /**
     * Tests that the first color button in the palette has the
     * correct style and user data

     * This verifies that the first button has "#000000" with
     * appropriate styling
     */
    @Test
    void setCorrectColorToButtonTest() {
            colorController.initializeColorPalette();
            Button button = (Button) colorPalette.getChildren().get(0);

            String style = button.getStyle();
            assertTrue(style.contains("-fx-background-color: #000000"));
            assertEquals("#000000", button.getUserData());
    }

    /**
     * Verifies that selecting a color button with a null colorPalette
     * does not throw an exception

     * This creates a colorController with an empty colorpalette, ensuring
     * that invoking #createColorButton does not throw an exception
     */
   @Test
    void colorSelectionOnNull(){
        ColorController colorController = new ColorController(new HBox());

        assertDoesNotThrow(() -> colorController
                        .createColorButton("#ff00ff").fire());
   }
}