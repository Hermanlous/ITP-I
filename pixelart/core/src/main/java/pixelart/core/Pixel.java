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
     * A boolean value indicating if the pixel is white (false) og black (true).
     */
    private boolean isBlack;

    /**
     * Constructs a Pixel with the given size.
     *
     * @param size The size of the pixel.
     */
    public Pixel(final int size) {
        this.pixelSize = size;
        this.canvas = new Canvas(pixelSize, pixelSize);
        this.isBlack = false;
        initializePixel();
    }

    /**
     * Initializes the pixel with a white colour by filling the canvas.
     * Is called when a pixel is created.
     */
    private void initializePixel() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, pixelSize, pixelSize);
    }

    /**
     * This method updates the colour of the pixel.
     *
     * @param isItBlack if true, it sets the
     *                  colour of that pixel to black
     */
    public void updateColor(final boolean isItBlack) {
        this.isBlack = isItBlack;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(isBlack ? Color.BLACK : Color.WHITE);
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
     * Checks if the pixel is black.
     *
     * @return {@code true} if the pixel is black; {@code false} otherwise.
     */
    public boolean isBlack() {
        return isBlack;
    }

    /**
     * Gets the color of the pixel as a string.
     *
     * @return "B" if the pixel is black; "W" if the pixel is white.
     */
    public String getColorAsString() {
        return isBlack ? "B" : "W";
    }
}
