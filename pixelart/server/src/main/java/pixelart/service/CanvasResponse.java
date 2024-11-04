package pixelart.service;

public class CanvasResponse {
    private String canvas;

    public CanvasResponse(String canvas) {

        this.canvas=canvas;
    }


    public String getCanvas() {
        return canvas;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }
}
