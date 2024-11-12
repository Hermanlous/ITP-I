package pixelart.ui;

import javafx.application.Platform;
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

    @Test
    void InitializeTest() {
        assertNotNull(colorController);
    }

    @Test
    void testInitializeColorPaletteTest() {
        colorController.initializeColorPalette();
        assertEquals(colorPalette.getChildren().size(), colors.length);
    }

    @Test
    void setNumberOfButtonsTest() {
            colorController.initializeColorPalette();
            assertEquals(8, colorPalette.getChildren().size());
    }

    @Test
    void setCorrectColorToButtonTest() {
            colorController.initializeColorPalette();
            Button button = (Button) colorPalette.getChildren().get(0);

            String style = button.getStyle();
            assertTrue(style.contains("-fx-background-color: #000000"));
            assertEquals("#000000", button.getUserData());
    }

   @Test
    void colorSelectionOnNull(){
        ColorController colorController = new ColorController(new HBox());

        assertDoesNotThrow(() -> colorController
                        .createColorButton("#ff00ff").fire());
   }
}