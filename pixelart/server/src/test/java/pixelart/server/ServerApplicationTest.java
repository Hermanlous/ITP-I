package pixelart.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ServerApplicationTest {

    @Test
    void contextLoads() {
    }

    @Test
    void shouldPrintWelcomeMessage(CapturedOutput output) {
        ServerApplication.main(new String[]{});

        assertTrue(output.getOut().contains("Hello pixelart!"),
                "Application should print welcome message");
    }
}