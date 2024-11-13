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

    /**
     * Mocked HttpClient to simulate HTTP requests in tests
     */
    @Mock
    private HttpClient mockHttpClient;

    /**
     * Mocked ObjectMapper to simulate JSON serialization
     * and deserialization in tests
     */
    @Mock
    private ObjectMapper mockObjectMapper;

    /**
     * Instance of the GridStateHandler class for testing
     */
    private GridStateHandler gridStateHandler;


    /**
     * Prepares the test environment before each test case.
     *
     * Initializes Mockito annotations for mock creation, and
     * sets up gridStateHandler with mocked instances of HttpClient and
     * ObjectMapper.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gridStateHandler = new GridStateHandler();
        gridStateHandler.setHttpClient(mockHttpClient);
        gridStateHandler.setObjectMapper(mockObjectMapper);
    }


    /**
     * Tests {@link GridStateHandler#sendCanvasToServer} for a successful PUT request.
     * Verifies that a 2D canvas array is serialized and sent via HTTP,
     * ensuring a successful response with status 200.
     *
     * @throws IOException if an IOException occurs
     * @throws InterruptedException if the instruction is interrupted
     */

    @Test
    void SuccessfullyPuttingCanvasTest() throws IOException, InterruptedException {
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

        assertDoesNotThrow(() -> gridStateHandler.sendCanvasToServer(canvasData));
        verify(mockHttpClient).send(any(HttpRequest.class), eq(BodyHandlers.ofString()));
        verify(mockObjectMapper).writeValueAsString(canvasData);
    }


    /**
     * Testing error handling when posting canvas data fails with status 500.
     * Simulates that {@link GridStateHandler#sendCanvasToServer} throws an IOException
     * with an appropriate error message when failing.
     *
     * @throws IOException when an IOException occurs
     * @throws InterruptedException if the instruction is interrupted
     */
    @Test
    void unsuccessfullyPostingCanvasTest() throws IOException, InterruptedException {
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
                gridStateHandler.sendCanvasToServer(canvasData)
        );
        assertTrue(exception.getMessage().contains("Failed to post canvas"));
        verify(mockHttpClient).send(any(HttpRequest.class), eq(BodyHandlers.ofString()));
        verify(mockObjectMapper).writeValueAsString(canvasData);
    }

    /**
     * Simulates a successful retrieval of canvas data through
     * {@link GridStateHandler#loadCanvas}
     * Verifies that the JSON response is properly deserialized to a 2D Array of colors.
     *
     * @throws IOException when an IOException occurs
     * @throws InterruptedException if the instruction is interrupted
     * */

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


    /**
     * Tests {@link GridStateHandler#loadCanvas} to ensure proper error handling
     * when retrieving canvas data that fails with an HTTP 500 status.
     *
     * The test simulates a server error response and checks that an
     * IOException with an appropriate error message is thrown.
     *
     * @throws IOException if an IOException occurs
     * @throws InterruptedException if the instruction is interrupted
     */

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