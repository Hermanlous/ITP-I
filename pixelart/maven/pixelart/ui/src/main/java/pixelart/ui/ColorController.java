package pixelart.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.function.Consumer;

public class ColorController {

    /** The HBox container that holds color selection buttons. */
    private final HBox colorPalette;

    /** The currently selected color in hexadecimal format. */
    private String currentColor = "#000000"; // Default black

    /** Handler that is triggered when a color is selected.  */
    private Consumer<String> onColorSelected;

    /** Array of hex color codes available. */
    private final String[] colors = {
            "#000000", // Black
            "#FFFFFF", // White
            "#ff0000", // Red
            "#00ff00", // Green
            "#0000ff", // Blue
            "#ffff00", // Yellow
            "#ff00ff", // Magenta
            "#00ffff", // Cyan
    };

    /**
     * Constructs a ColorController with the specified
     * HBox container for color selection buttons.
     *
     * @param theColorPalette the HBox container where
     * color buttons are displayed.
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
    public ColorController(final HBox theColorPalette) {
        this.colorPalette = theColorPalette;
    }

    /**
     * Initializes the color palette by creating and adding buttons for each
     * color defined in the palette. Each button represents an avaliable color.
     */
    public void initializeColorPalette() {
        for (String hexColor : colors) {
            Button colorButton = createColorButton(hexColor);
            colorPalette.getChildren().add(colorButton);
        }
    }

    /**
     * Creates a color button with the specified color and sets its appearance.
     *
     * @param colorHex the hexadecimal color code to set as
     * the button's background.
     * @return a Button instance styled with the specified color.
     */
    Button createColorButton(final String colorHex) {
        Button button = new Button();

        if (colorHex.equals(currentColor)) {
            button.setStyle(
                    "-fx-background-color: "
                            + colorHex
                            + ";"
                            + "-fx-min-width: 40px;"
                            + "-fx-min-height: 40px;"
                            + "-fx-pref-width: 40px;"
                            + "-fx-pref-height: 40px;"
            );
        } else {
            button.setStyle(
                    "-fx-background-color: "
                            + colorHex
                            + ";"
                            + "-fx-min-width: 30px;"
                            + "-fx-min-height: 30px;"
                            + "-fx-pref-width: 30px;"
                            + "-fx-pref-height: 30px;"
                            + "-fx-border-radius: 15px;"
            );
        }

        button.setUserData(colorHex);
        button.setOnAction(e -> {
            currentColor = colorHex;
            if (onColorSelected != null) {
                onColorSelected.accept(currentColor);
            }

            colorPalette.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    Button colorButton = (Button) node;
                    boolean isSelected = (colorButton == button);

                    if (isSelected) {
                        colorButton.setStyle(
                                "-fx-background-color: "
                                        + colorHex
                                        + ";"
                                        + "-fx-min-width: 40px;"
                                        + "-fx-min-height: 40px;"
                                        + "-fx-pref-width: 40px;"
                                        + "-fx-pref-height: 40px;"
                        );
                    } else {
                        colorButton.setStyle(
                                "-fx-background-color: "
                                        + colorButton.getUserData()
                                        + ";"
                                        + "-fx-min-width: 30px;"
                                        + "-fx-min-height: 30px;"
                                        + "-fx-pref-width: 30px;"
                                        + "-fx-pref-height: 30px;"
                        );
                    }
                }
            });
        });

        return button;
    }

    /**
     * Sets a handler to be invoked when a color is selected.
     * The handler receives the selected color's hexadecimal
     * code as a parameter.
     *
     * @param handler a Consumer that processes the selected color code.
     */
    public void setOnColorSelected(final Consumer<String> handler) {
        this.onColorSelected = handler;
    }
}
