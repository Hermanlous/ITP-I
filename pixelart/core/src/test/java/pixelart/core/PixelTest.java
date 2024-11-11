package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PixelTest {

    private int pixelSize;
    private Pixel pixel;

    @BeforeEach
    public void setUp(){
        this.pixelSize = 10;
        this.pixel = new Pixel(this.pixelSize);

    }

    @Test
    void successfullyInitializationTest(){
        assertNotNull(pixel);
        assertTrue(pixel.getPixelSize() > 0);
    }


    @Test
    void successfullyUpdateColorTest(){
        pixel.updateColor("#000000");
        assertEquals("#000000", pixel.getCurrentColor());
    }

    @Test
    void pixelSizeTest(){
        assertEquals(pixelSize, pixel.getPixelSize());
    }

    @Test
    void successfullyRetrievingCanvasTest(){
        assertNotNull(pixel.getCanvas());
    }
}
