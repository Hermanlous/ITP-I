package pixelart.core;

public class Grid {

    /**
     * The grid has int gridSize rows and coloumns.
     */
    private final int gridSize;

    /**
     * A 2D array that holds all the pixels.
     * Each pixel is a {@link Pixel} object
     */
    private final Pixel[][] pixels; //[][] = all pixels, not just one

    /**
     * Handles the state, saving and loading of the grid.
     */
    private GridStateHandler gridStateHandler;

    /**
     * The defaultet size of our pixel.
     * Each square the app has, use 10x10 pixels on screen to represent it.
     */
    private static final int DEFAULT_PIXEL_SIZE = 10;

    /**
     * Constructs a new instance of the {@code Grid} class.
     * This makes a grid structure consisting of pixels.
     *
     * @param size the length of each side of the square grid.
     * @param pixelSize the size of each pixel.
     */
    public Grid(final int size, final int pixelSize) {
        this.gridSize = size;
        this.pixels = new Pixel[gridSize][gridSize];
        initializeGrid(pixelSize);
    }

    /**
     * Initializes the grid with the default pixel size 10.
     */
    public void initializeGrid() {
        initializeGrid(DEFAULT_PIXEL_SIZE);
    }

    /**
     * Initializes the grid by creating a 2D array of pixels with input size.
     * Each pixel has the same size, pixelsize.
     *
     * @param pixelSize defines the width and height of the pixel in pixels.
     *
     */
    public void initializeGrid(final int pixelSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                pixels[row][column] = new Pixel(pixelSize);
            }
        }
    }

    /**
     * Updates the colour of a specific pixel.
     * Black or white TODO update when colours are added
     *
     * @param row row index of pixel to change colour.
     * @param col column index of pixel to change colour.
     * @param isBlack boolean, true if pixel is black, false if pixel is white.
     * Atlast the method saves the grid again with the updated pixelcolour.
     */
    public void updatePixel(
        final int row,
        final int col,
        final boolean isBlack) {
        pixels[row][col].updateColor(isBlack);
        gridStateHandler.saveJsonState(); // Call to save state here
    }

    /**
     * Return the size of the grid.
     * @return size of grid as integer.
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Return a specific pixel.
     *
     * @param row is the row index of the pixel.
     * @param col is the column index of the pixel.
     * @return returns one {@link Pixel}
     */
    public Pixel getPixel(final int row, final int col) { //This sends one pixel
        return pixels[row][col];
    }

    /**
     * Returns all the pixels in the grid.
     * @return a 2D array of {@link Pixel} objects. All pixels in the grid.
     */
    public Pixel[][] getAllPixels() { //This sends all of them
        return pixels;
    }

    /**
     * Returns a 2D array representing the grid in a JSON-compatible format.
     * Each pixel is represented as a string: "B" for black. "W" for white.
     *
     * @return a 2D array of strings. "B" or "W"
     * TODO update with colours
     */
    public String[][] getJsonGrid() {
        String[][] jsonGrid = new String[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                jsonGrid[row][col] = pixels[row][col].isBlack() ? "B" : "W";
            }
        }
        return jsonGrid;
    }
}
