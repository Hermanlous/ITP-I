package pixelart.core;
import java.io.IOException;


public class Grid {
    private static final int DEFAULT_PIXEL_SIZE = 10;
    private static final int DEFAULT_GRID_SIZE = 100;
    private String[][] currentState;
    private int gridSize;
    private Pixel[][] pixels; //Here we need the [][] because we have many, not just one
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
        this.gridStateHandler = new GridStateHandler(); //Tried to do this to make the test happy.
        initializeEmptyGrid(pixelSize);
    }

   public Grid(String[][] currentState, int pixelSize) throws IOException, InterruptedException {
        try{
            this.gridStateHandler = new GridStateHandler();
            this.currentState = gridStateHandler.loadCanvas();
            this.gridSize = currentState.length;
            this.pixels = new Pixel[gridSize][gridSize];

            initializeGridFromState(currentState, pixelSize);

        } catch (Exception e) {
            System.out.println("Error loading grid");
            this.gridSize = DEFAULT_GRID_SIZE;
            initializeEmptyGrid(pixelSize);
        }
    }

    private void initializeEmptyGrid(int pixelSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                pixels[row][col] = new Pixel(pixelSize);
            }
        }
    }

    public void initializeGridFromState(
            String[][] currentState,
            int pixelSize) throws IOException, InterruptedException {
        for (int row = 0; row < gridSize; row ++){
            for (int column = 0; column < gridSize; column ++){
                pixels[row][column] = new Pixel(pixelSize);
                boolean isBlack = currentState[row][column].equals("B");
                updatePixel(row, column, isBlack);
            }
        }
    }


    public void updatePixel (
            int row,
            int col,
            boolean isBlack) {
        pixels[row][col].updateColor(isBlack);
    }

    /**
     * Return the size of the grid.
     * @return size of grid as integer.
     */
    public int getGridSize() {
        return gridSize;
    }

    public Pixel getPixel(int row, int col) {
        return pixels[row][col];
    }

    public Pixel[][] getAllPixels() {
        return pixels;
    }

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
