package pixelart.service;

public class CanvasResponse {

    /**
     * The canvas data represented as a string.
     */
    private String canvas;

    /**
     * Constructs a new CanvasResponse with the specified canvas data.
     *
     * @param theCanvas The canvas data as a string to be set in the response.
     */
    public CanvasResponse(final String theCanvas) {
        this.canvas = theCanvas;
    }

    /**
     * Returns the canvas data.
     *
     * @return The canvas data as a string.
     */
    public String getCanvas() {
        return canvas;
    }

    /**
     * Sets the canvas data.
     *
     * @param theCanvas The new canvas data as a string.
     */
    public void setCanvas(final String theCanvas) {
        this.canvas = theCanvas;
    }
}
