package pixelart.ui;


import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.function.Consumer;

public class ColorController {
    private final HBox colorPalette;
    private String currentColor = "#000000"; // Default black
    private Consumer<String> onColorSelected;

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

    public ColorController(HBox colorPalette) {
        this.colorPalette = colorPalette;
    }

    public void initializeColorPalette() {
        for (String hexColor : colors) {
            Button colorButton = createColorButton(hexColor);
            colorPalette.getChildren().add(colorButton);
        }
    }

    private Button createColorButton(final String colorHex) {
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

    public void setOnColorSelected(Consumer<String> handler) {
        this.onColorSelected = handler;
    }
}
