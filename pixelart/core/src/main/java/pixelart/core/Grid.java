package pixelart.core;

public class Grid {
    private final int gridSize;
    private final Pixel[][] pixels; //Here we need the [][] because we have many, not just one
    private GridStateHandler gridStateHandler;

    public Grid (int gridSize, int pixelSize) {
        this.gridSize = gridSize;
        this.pixels = new Pixel[gridSize][gridSize];
        this.gridStateHandler = gridStateHandler; //Tried to do this to make the test happy.
        initializeGrid(pixelSize);
    }

    public void initializeGrid() {
        initializeGrid(10); // Default pixel size. To avoid null pointer exception. 
    }

    public void initializeGrid (int pixelSize) {
        for (int row = 0; row < gridSize; row ++){
            for (int column = 0; column < gridSize; column ++){
                pixels[row][column] = new Pixel(pixelSize);
            }
        }
    }

    public void updatePixel (int row, int col, boolean isBlack) {
        pixels[row][col].updateColor(isBlack);
        gridStateHandler.saveJsonState(); // Call to save state here
    }

    public int getGridSize() {
        return gridSize;
    }

    public Pixel getPixel(int row, int col) { //This sends one pixel
        return pixels[row][col];
    }

    public Pixel[][] getAllPixels() { //This sends all of them
        return pixels; 
    }

    public String[][] getJsonGrid() { //This method is here to please the test ... not good :( TODO
        String[][] jsonGrid = new String[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                jsonGrid[row][col] = pixels[row][col].isBlack() ? "B" : "W"; 
            }
        }
        return jsonGrid;
    }
}
