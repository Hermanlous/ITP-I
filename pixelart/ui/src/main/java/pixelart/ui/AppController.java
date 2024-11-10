package pixelart.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;

/**
 * AppController is the main controller class for our JavaFX application.
 * It controls the interaction between the user and our canvas.
 * The user is able to draw and erase on the different pixels.
 * This is done by using left and right mouse click.
 */
public class AppController {

    /** The main grid containing the canvas element.*/
    @FXML
    private GridPane gridPane;

    /**
     * The color palette displayed as a set of color buttons.
     */
    @FXML
    private HBox colorPalette;

    /** The grid model containing our pixeldata.*/
    private Grid grid;

    /** Managed saving and loading gridstate from server.*/
    private GridStateHandler gridStateHandler;

    /** The size of the width of the grid. */
    private final int gridSizeWidth = 60;

    /** The size of the height of the grid. */
    private final int gridSizeHeight = 40;

    /** Size of each pixel square in the grid.*/
    private final int pixelSize = 10;

    /** Stores the current state of each pixel in grid.*/
    private String[][] currentState;

    private ColorManager colorManager;
    private GridManager gridManager;



    /**
     * Initializes the controller as well as the canvas
     * Tries to retrieve canvas from server. Else a new one.
     *
     * @throws IOException if there´s an error initializing
     * @throws InterruptedException if the loading from server process fails
     * */

    @FXML
    public void initialize() throws IOException, InterruptedException {
        this.colorManager = new ColorManager(colorPalette);
        colorManager.initializeColorPalette();

        try {
            this.gridStateHandler = new GridStateHandler();
            String[][] currentState = gridStateHandler.loadCanvas();
            grid = new Grid(currentState, GridManager.PIXEL_SIZE);
        } catch (Exception e) {
            this.grid = new Grid(GridManager.GRID_SIZE_WIDTH, GridManager.GRID_SIZE_HEIGHT, GridManager.PIXEL_SIZE);
        }

        this.gridManager = new GridManager(grid, gridPane, gridStateHandler);
        gridManager.initializeGridPane();

        // Connect color selection to grid manager
        colorManager.setOnColorSelected(color -> gridManager.setCurrentColor(color));
    }
}


    /*private void initializeGridPane() {
        Pixel[][] pixels = grid.getAllPixels();
        for (int r = 0; r < gridSizeHeight; r++) {
            for (int c = 0; c < gridSizeWidth; c++) {
                Canvas canvas = pixels[r][c].getCanvas();
                gridPane.add(canvas, c, r);
                int row = r;
                int column = c;
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> pixelClick(row, column, event));
            }
        }
    }

    private void initializeColorPalette() {
        for (String hexColor : colors) {
            Button colorButton = createColorButton(hexColor);
            colorPalette.getChildren().add(colorButton);
        }
    }

    private Button createColorButton(final String colorHex) {
        Button button = new Button();

        // Initial style for the button
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
                + "-fx-border-radius: 15px;" // Maintain round appearance
            );
        }

        // Store the color hex for reference
        button.setUserData(colorHex);

        // Set up action for button selection
        button.setOnAction(e -> {
            currentColor = colorHex;

            // Update visual selection state for each button
            colorPalette.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    Button colorButton = (Button) node;
                    boolean isSelected = (colorButton == button);

                    // Define styles based on selection state
                    if (isSelected) {
                        // Increase size for the selected button
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
                        // Style for the unselected button
                        colorButton.setStyle(
                            "-fx-background-color: "
                            + colorButton.getUserData()
                            + ";"
                            + // Use the stored color
                            "-fx-min-width: 30px;"
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
     * Handles mouse click events on a pixel to update the colour.
     *
     * @param row the row index of the pixel clicked.
     * @param column the column index of the pixel clicked.
     * @param event the mouse event triggered by the click.
     */
    /*private void pixelClick(
        final int row,
        final int column,
        final MouseEvent event) {

        String colorToApply;
        if (event.getButton() == MouseButton.PRIMARY) {
            colorToApply = currentColor;
        } else {
            colorToApply = "#FFFFFF"; // Right click for eraser (white)
        }
        grid.getPixel(row, column).updateColor(colorToApply);
        saveCanvasToServer();
    }

    private void saveCanvasToServer() {
        try {
            // Use the existing gridStateHandler instead of creating a new one
            GridStateHandler service = new GridStateHandler();
            String[][] currentGrid = grid.getJsonGrid();
            service.postCanvas(currentGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/
