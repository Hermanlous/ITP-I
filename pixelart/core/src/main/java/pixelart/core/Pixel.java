package pixelart.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pixel {

    /**
     * The canvas that represents this specific pixel visually.
     */
    private final Canvas canvas;

    /**
     * The size of our pixel.
     * Each square the app has, use 10x10 pixels on screen to represent it.
     */
    private final int pixelSize;

    /**
     * The current active color.
     * The color to use when user clicks on a pixel.
     */
    private String currentColor;

    /**
     * Constructs a Pixel with the given size.
     *
     * @param size The size of the pixel.
     */
    public Pixel(final int size) {
        this.pixelSize = size;
        this.canvas = new Canvas(pixelSize, pixelSize);
        this.currentColor = "#FFFFFF"; // Default white color
        initializePixel();
    }

    /**
     * Initializes the pixel with a white colour by filling the canvas.
     * Is called when a pixel is created.
     */
    private void initializePixel() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.web(currentColor)); // Allows hex color strings
        gc.fillRect(0, 0, pixelSize, pixelSize);
    }

    /**
     * This method updates the colour of the pixel.
     *
     * @param hexColor string of colror value on hex format.
     */
    public void updateColor(final String hexColor) {
        this.currentColor = hexColor;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.web(hexColor));
        gc.fillRect(0, 0, pixelSize, pixelSize);
    }

    /**
     * Returns the canvas that represents the current pixel.
     *
     * @return the canvas object associated with this pixel
     */
    public Canvas getCanvas() {  //TODO do we need this?
        return canvas;
    }

    /**
     * Returns the size of the pixel.
     *
     * @return the size of the pixel in pixels.
     */
    public int getPixelSize() {
        return pixelSize;
    }

    /**
     * Gets the color of the pixel as a string.
     *
     * @return current active color
     */
    public String getCurrentColor() {
        return currentColor;
    }
}
