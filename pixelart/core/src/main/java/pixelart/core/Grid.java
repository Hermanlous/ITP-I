package pixelart.core;

import java.io.IOException;

public class Grid {
    /**
     * The defaultet size of our pixel.
     * Each square the app has, use 10x10 pixels on screen to represent it.
     */
    private static final int DEFAULT_PIXEL_SIZE = 10;

    /**
     * The defaultet width of our grid.
     */
    private static final int DEFAULT_GRID_WIDTH = 60;

    /**
     * The defaultet height of our grid.
     */
    private static final int DEFAULT_GRID_HEIGHT = 40;

    /**
     * The width of the grid in pixels.
     */
    private final int gridSizeWidth;

    /**
     * The height of the grid in pixels.
     */
    private final int gridSizeHeight;

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
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @param pixelSize the size of each pixel.
     */
    public Grid(final int width, final int height, final int pixelSize) {
        this.gridSizeWidth = width;
        this.gridSizeHeight = height;
        this.pixels = new Pixel[gridSizeHeight][gridSizeWidth];
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

        this.gridSizeHeight =
        newState.length > 0 ? newState[0].length : DEFAULT_GRID_HEIGHT;
        this.gridSizeWidth =
        newState[0].length > 0 ? newState.length : DEFAULT_GRID_WIDTH;

        try {
            this.gridStateHandler = new GridStateHandler();
            this.currentState = gridStateHandler.loadCanvas();
            // this.gridSizeWidth = newState.length;
            // this.gridSizeHeight = newState[0].length;
            this.pixels = new Pixel[gridSizeHeight][gridSizeWidth];
            initializeGridFromState(newState, pixelSize);
        } catch (Exception e) {
            System.out.println("Error loading grid");
            // this.gridSizeWidth = DEFAULT_GRID_WIDTH;
            // this.gridSizeHeight = DEFAULT_GRID_HEIGHT;
            initializeEmptyGrid(pixelSize);
        }
    }

    /**
     * Initializes an empty grid with each {@code Pixel} set to pixelsize.
     *
     * @param pixelSize the size of each pixel in the grid.
     */
    private void initializeEmptyGrid(final int pixelSize) {
        this.pixels = new Pixel[gridSizeHeight][gridSizeWidth];
        this.currentState = new String[gridSizeHeight][gridSizeWidth];

        for (int row = 0; row < gridSizeHeight; row++) {
            for (int col = 0; col < gridSizeWidth; col++) {
                pixels[row][col] = new Pixel(pixelSize);
            }
        }
    }

    /**
     * Initializes the grid from a 2D array of pixel states
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
        for (int row = 0; row < gridSizeHeight; row++) {
            for (int column = 0; column < gridSizeWidth; column++) {
                pixels[row][column] = new Pixel(pixelSize);
                updatePixel(row, column, initialState[row][column]);
            }
        }
    }

    /**
     * Updates the colour of a specific pixel.
     *
     * @param row row index of pixel to change colour.
     * @param col column index of pixel to change colour.
     * @param hexColor string, color value.
     */
    public void updatePixel(
            final int row, final int col, final String hexColor) {
        pixels[row][col].updateColor(hexColor);
    }

    /**
     * Return the width of the grid.
     * @return width of grid as integer.
     */
    public int getgridSizeWidth() {
        return gridSizeWidth;
    }

    /**
     * Return the height of the grid.
     * @return height of grid as integer.
     */
    public int getgridSizeHeight() {
        return gridSizeHeight;
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
        return pixels.clone();
    }

    /**
     * Returns a 2D array representing the grid in a JSON-compatible format.
     * Each pixel is represented as a string on the format #RRGGBB
     *
     * @return a 2D array of strings.
     */
    public String[][] getJsonGrid() {
        String[][] jsonGrid = new String[gridSizeHeight][gridSizeWidth];
        for (int row = 0; row < gridSizeHeight; row++) {
            for (int col = 0; col < gridSizeWidth; col++) {
                jsonGrid[row][col] = pixels[row][col].getCurrentColor();
            }
        }
        return jsonGrid;
    }

    protected void setGridStateHandler(GridStateHandler gridStateHandler) {
        this.gridStateHandler = gridStateHandler;
    }

}
