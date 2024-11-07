package pixelart.core;

import java.io.IOException;

public class Grid {
     /**
     * The defaultet size of our pixel.
     * Each square the app has, use 10x10 pixels on screen to represent it.
     */
    private static final int DEFAULT_PIXEL_SIZE = 10;

    /**
     * The defaultet size of our grid.
     */
    private static final int DEFAULT_GRID_SIZE = 100;

    /**
     * The size of the grid accoring to users wants.
     */
    private int gridSize;

    /**
     * A 2D array representing the initial state of each pixel.
     * Stores the state as a 2D array of strings, where each entry represents
     * the color of a pixel in the grid ("B" for black, "W" for white).
     */
    private String[][] currentState;

    /**
     * A 2D array that holds all the pixels.
     * Each pixel is a {@link Pixel} object
     */
    private Pixel[][] pixels; //[][] = all pixels, not just one

    /**
     * Handles the state, saving and loading of the grid.
     */
    private GridStateHandler gridStateHandler;

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
        this.gridStateHandler = new GridStateHandler();
        initializeEmptyGrid(pixelSize);
    }

    /**
     * Constructs a {@code Grid} from an existing state.
     * Initializes the grid based on the 2D array state and pixel size.
     * If it can't find the gris it defaults to an empty grid.
     *
     * @param newState 2D array representing the initial state of each pixel
     * ("B" for black, "W" for white).
     * @param pixelSize the size of each pixel in the grid.
     * @throws IOException if an error occurs during loading.
     * @throws InterruptedException if the process is interrupted.
     */
    public Grid(final String[][] newState, final int pixelSize)
        throws IOException, InterruptedException {
        try {
            this.gridStateHandler = new GridStateHandler();
            this.currentState = gridStateHandler.loadCanvas();
            this.gridSize = newState.length;
            this.pixels = new Pixel[gridSize][gridSize];

            initializeGridFromState(currentState, pixelSize);

        } catch (Exception e) {
            System.out.println("Error loading grid");
            this.gridSize = DEFAULT_GRID_SIZE;
            initializeEmptyGrid(pixelSize);
        }
    }

    /**
     * Initializes an empty grid with each {@code Pixel} set to pixelsize.
     *
     * @param pixelSize the size of each pixel in the grid.
     */
    private void initializeEmptyGrid(final int pixelSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                pixels[row][col] = new Pixel(pixelSize);
            }
        }
    }

    /**
     * Initializes the grid from a 2D array of pixel states
     * ("B" for black, "W" for white).
     * TODO update with colours
     * Each pixel is created and updated based on the
     * state in {@code currentState}.
     *
     * @param initialState a 2D array of strings representing pixel colors.
     * @param pixelSize the size of each pixel.
     * @throws IOException if an error occurs while loading the grid state.
     * @throws InterruptedException if the thread is interrupted.
     */

    public void initializeGridFromState(
            final String[][] initialState,
            final int pixelSize) throws IOException, InterruptedException {
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                pixels[row][column] = new Pixel(pixelSize);
                boolean isBlack = initialState[row][column].equals("B");
                updatePixel(row, column, isBlack);
            }
        }
    }

    /**
     * Updates the color of the specified pixel in the grid.
     *
     * @param row the row index of the pixel.
     * @param col the column index of the pixel.
     * @param isBlack {@code true} if the pixel should be set to black,
     *                {@code false} if it should be set to white.
     * TODO update with colours
     */
    public void updatePixel(
            final int row,
            final int col,
            final boolean isBlack) {
        pixels[row][col].updateColor(isBlack);
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
