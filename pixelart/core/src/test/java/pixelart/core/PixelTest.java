package pixelart.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PixelTest {

    private int pixelSize;
    private Pixel pixel;

    @BeforeEach
    public void setUp(){
        this.pixelSize = 10;
        this.pixel = new Pixel(this.pixelSize);

    }

    @Test
    public void testReturnPixelSize(){
        assertEquals(pixelSize, pixel.getPixelSize());
    }




}
