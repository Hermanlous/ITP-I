package pixelart.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GridStateHandlerTest {

    @Mock
    private HttpClient mockHttpClient;

    private GridStateHandler gridStateHandler;
    private ObjectMapper objectMapper;

    /*Claude AI:
    How do I set up the test with Mock and a mockHttpClient
     from here: */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        gridStateHandler = new GridStateHandler() {
            @Override
            protected HttpClient createHttpClient() {
                return mockHttpClient;
            }
        };
    }
    /*to here*/

    @Test
    void postCanvas_Success() throws IOException, InterruptedException {
        // Arrange
        String[][] canvasData = {
                {"#FF0000", "#00FF00"},
                {"#0000FF", "#FFFFFF", "#000000"}
        };

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = (HttpResponse<String>) mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("Success");
        when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
                .thenReturn(mockResponse);

        assertDoesNotThrow(() -> gridStateHandler.postCanvas(canvasData));
    }

    @Test
    void postCanvas_Failed() throws IOException, InterruptedException {
        // Arrange
        String[][] canvasData = {
                {"#FF0000", "#00FF00"},
                {"#0000FF", "#FFFFFF", "#000000"}
        };

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = (HttpResponse<String>) mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(500);
        when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
                .thenReturn(mockResponse);

        Exception exception = assertThrows(IOException.class, () ->
                gridStateHandler.postCanvas(canvasData)
        );
        assertTrue(exception.getMessage().contains("Failed to post canvas"));
    }
}