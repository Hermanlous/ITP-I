package pixelart.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
* Test suite for verifying the basic startup behavior
* of the Pixelart server.
*
* This class tests if the application context loads successfully
* and that the application outputs the welcome message at start.
*/
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ServerApplicationTest {

    @Test
    void contextLoads() {
    }

    /**
     * Verifies that the server application prints "Hello pixelart!" on startup.
     *
     * @param output Captures console output during the test
     */
    @Test
    void shouldPrintWelcomeMessage(CapturedOutput output) {
        ServerApplication.main(new String[]{});

        assertTrue(output.getOut().contains("Hello pixelart!"),
                "Application should print welcome message");
    }
}