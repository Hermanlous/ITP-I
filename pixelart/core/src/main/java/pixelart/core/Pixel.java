package pixelart.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pixel {
    private final Canvas canvas; //In the original gridmanager we had canvas[][], because we initialized the grid. Here we only have one canvas. 
    private final int pixelSize;
    private boolean isBlack;

    public Pixel (int pixelSize) {
        this.pixelSize = pixelSize;
        this.canvas = new Canvas(pixelSize, pixelSize);
        this.isBlack = false;
        initializePixel();
    }

    private void initializePixel() {
        GraphicsContext gc = canvas.getGraphicsContext2D(); //gc is short for graphiscContext
        gc.setFill(Color.WHITE);
        gc.fillRect (0, 0, pixelSize, pixelSize);
    }

    public void updateColor(boolean isBlack) {
        this.isBlack = isBlack; 
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(isBlack ? Color.BLACK : Color.WHITE);
        gc.fillRect(0, 0, pixelSize, pixelSize);
    }
    
    public Canvas getCanvas() {  //Do we need this? TODO I don't know. 
        return canvas;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public String getColorAsString() {
        return isBlack ? "B" : "W";  
    }
}
