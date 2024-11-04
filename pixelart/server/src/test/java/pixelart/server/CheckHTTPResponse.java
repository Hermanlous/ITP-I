package pixelart.server;
//https://www.youtube.com/watch?v=5kOGdZmpSDI sourced 21.10.2024 08:50

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckHTTPResponse {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void stringMatch(){
        assertEquals("Pixelart API is running",
                testRestTemplate.getForObject("http://localhost:" + port + "/"+ "run", String.class));

    }
}
