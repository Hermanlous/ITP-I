package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PixelTest {

    /**
     * the pixelsize used for testing
     */
    private int pixelSize;

    /**
     * The Pixel used for testing
     */
    private Pixel pixel;

    /**
     * This sets up the testing environment
     * before each test case.

     * It initializes the pixelsize and a
     * Pixel based on this size
     */
    @BeforeEach
    public void setUp(){
        this.pixelSize = 10;
        this.pixel = new Pixel(this.pixelSize);
    }

    /**
     * Tests the successful initialization of a Pixel

     * Ensures that the Pixel has been initialized and
     * that the pixelSize is bigger than 0
     */
    @Test
    void successfullyInitializationTest(){
        assertNotNull(pixel);
        assertTrue(pixel.getPixelSize() > 0);
    }

    /**
     * Tests the successful update of color for a Pixel

     * Verifies that the current color of the Pixel
     * equals the color it was updated to have.
     */
    @Test
    void successfullyUpdateColorTest(){
        pixel.updateColor("#000000");
        assertEquals("#000000", pixel.getCurrentColor());
    }

    /**
     * Tests that a Pixel has been initialized with correct size
     *
     * This test ensures the pixel has been initialized correctly,
     * with correct size as parameter.
     */
    @Test
    void pixelSizeTest(){
        assertEquals(pixelSize, pixel.getPixelSize());
    }

    /**
     * Tests that the pixel is initialized

     * This tests that the canvas correlated to the Pixel
     * is non-null and therefore a valid Pixel.
     * */
    @Test
    void successfullyRetrievingCanvasTest(){
        assertNotNull(pixel.getCanvas());
    }
}
