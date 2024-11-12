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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GridStateHandlerTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private ObjectMapper mockObjectMapper;

    private GridStateHandler gridStateHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gridStateHandler = new GridStateHandler();
        gridStateHandler.setHttpClient(mockHttpClient);
        gridStateHandler.setObjectMapper(mockObjectMapper);
    }

    @Test
    void SuccessfullyPostingCanvasTest() throws IOException, InterruptedException {
        String[][] canvasData = {
                {"#FF0000", "#00FF00"},
                {"#0000FF", "#FFFFFF", "#000000"}
        };

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("Success");
        when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
                .thenReturn(mockResponse);
        when(mockObjectMapper.writeValueAsString(any())).thenReturn("mockJsonString");

        assertDoesNotThrow(() -> gridStateHandler.postCanvas(canvasData));
        verify(mockHttpClient).send(any(HttpRequest.class), eq(BodyHandlers.ofString()));
        verify(mockObjectMapper).writeValueAsString(canvasData);
    }

    @Test
    void unsuccessfullyPostingCanvasTest() throws IOException, InterruptedException {
        // Arrange
        String[][] canvasData = {
                {"#FF0000", "#00FF00"},
                {"#0000FF", "#FFFFFF", "#000000"}
        };

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(500);
        when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
                .thenReturn(mockResponse);
        when(mockObjectMapper.writeValueAsString(any())).thenReturn("mockJsonString");

        Exception exception = assertThrows(IOException.class, () ->
                gridStateHandler.postCanvas(canvasData)
        );
        assertTrue(exception.getMessage().contains("Failed to post canvas"));
        verify(mockHttpClient).send(any(HttpRequest.class), eq(BodyHandlers.ofString()));
        verify(mockObjectMapper).writeValueAsString(canvasData);
    }

    @Test
    void successfullyRetrievingCanvasTest() throws IOException, InterruptedException {
        String[][] expectedData = {
                {"#FF0000", "#00FF00"},
                {"#0000FF", "#FFFFFF"}
        };

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        String mockJsonResponse = "[['#FF0000','#00FF00'],['#0000FF','#FFFFFF']]";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
                .thenReturn(mockResponse);
        when(mockObjectMapper.readValue(mockJsonResponse, String[][].class))
                .thenReturn(expectedData);

        String[][] result = gridStateHandler.loadCanvas();

        assertNotNull(result);
        assertArrayEquals(expectedData, result);

        verify(mockHttpClient).send(any(HttpRequest.class), eq(BodyHandlers.ofString()));
        verify(mockObjectMapper).readValue(mockJsonResponse, String[][].class);
    }

    @Test
    void unsuccessfullyRetrievingCanvasTest() throws IOException, InterruptedException {
        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(500);
        when(mockResponse.body()).thenReturn("Internal Server Error");
        when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
                .thenReturn(mockResponse);

        Exception exception = assertThrows(IOException.class, () -> {
            gridStateHandler.loadCanvas();
        });

        assertTrue(exception.getMessage().contains("Failed to fetch canvas data: HTTP 500"));
        verify(mockHttpClient).send(any(HttpRequest.class), eq(BodyHandlers.ofString()));
        verifyNoInteractions(mockObjectMapper); // ObjectMapper shouldn't be called on error
    }
}