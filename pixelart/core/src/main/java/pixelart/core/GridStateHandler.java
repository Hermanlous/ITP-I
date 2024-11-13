package pixelart.core;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GridStateHandler {

    /**
     * The HTTP client used to send and receive HTTP requests.
     */
    private HttpClient httpClient;

    /**
     * The object mapper used to convert between Java objects and JSON strings.
     */
    private ObjectMapper objectMapper;

    /**
     * The URI endpoint for accessing the canvas on server.
     */
    private static final String CANVAS_URI = "http://localhost:8080/canvas";

    /**
     * The HTTP status code for a successful request (200 OK).
     */
    private static final int HTTP_OK = 200;

    /**
     * The upper limit for successful HTTP status codes (less than 300).
     */
    private static final int HTTP_MAX_SUCCESS = 300;

    /**
     * Initializes the HTTP client and object mapper for server interaction.
     */
    public GridStateHandler() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sets the {@link HttpClient} used for server communication.
     * This client handles all HTTP requests for saving and loading
     * the canvas state.
     *
     * @param theHttpClient the {@code HttpClient} to use for HTTP operations.
     */
    public void setHttpClient(final HttpClient theHttpClient) {
        this.httpClient = theHttpClient;
    }

     /**
     * Sets the {@link ObjectMapper} for JSON serialization and deserialization.
     * The object mapper converts the canvas data to JSON format and parses
     * JSON responses from the server.
     *
     * @param theObjectMapper the {@code ObjectMapper} instance for
     * JSON processing.
     */
     @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
    public void setObjectMapper(final ObjectMapper theObjectMapper) {
        this.objectMapper = theObjectMapper;
    }

    /**
     * Posts the current canvas to the server via a PUT request.
     *
     * @param canvasData A String array representing the canvas grid.
     * @throws IOException          In case of an IO exception
     * @throws InterruptedException If the operation is interrupted.
     */
    public void postCanvas(final String[][] canvasData)
        throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(canvasData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CANVAS_URI))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HTTP_OK) {
            System.out.println("true");
        } else {
            throw new IOException("Failed to post canvas: HTTP "
            + response.statusCode());
        }
    }

    /**
     * Loads the canvas state from the server via a GET request.
     * @throws IOException in case of an IO exception
     * @throws InterruptedException if operation is interrupted
     * @return A 2D array representing the canvas state.
     */
    public String[][] loadCanvas() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(CANVAS_URI))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
        /* Claude AI: "How can I ensure that the response is a String array?
          from here: "*/
        if (response.statusCode() >= HTTP_OK
        && response.statusCode() < HTTP_MAX_SUCCESS) {
            try {
                return objectMapper.readValue(
                    response.body(),
                    String[][].class);
            } catch (Exception e) {
                throw new IOException("Failed to parse canvas data: "
                + e.getMessage());
            }
        /* To here*/
        } else {
            throw new IOException("Failed to fetch canvas data: HTTP "
            + response.statusCode());
        }
    }
}
