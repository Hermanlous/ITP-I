package pixelart.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GridStateHandler {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String uri = "http://localhost:8080/canvas";


    public GridStateHandler (){
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Posts the current canvas to the server via a PUT request.
     *
     * @param canvasData A String array representing the canvas grid.
     * @throws IOException          In case of an IO exception
     * @throws InterruptedException If the operation is interrupted.
     */
    public void postCanvas(String[][] canvasData) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(canvasData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println(response.body());
        } else {
            throw new IOException("Failed to post canvas: HTTP " + response.statusCode());
        }
    }

    /**
     * Loads the canvas state from the server via a GET request.
     * @throws IOException in case of an IO exception
     * @throws InterruptedException if operation is interrupted
     */
    public String[][] loadCanvas() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
        /* Claude AI: "How can I ensure that the response is a String array?
          from here: "*/
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            try {
                return objectMapper.readValue(response.body(), String[][].class);
            } catch (Exception e) {
                throw new IOException("Failed to parse canvas data: " + e.getMessage());
            }
        /* To here*/
        } else {
            throw new IOException("Failed to fetch canvas data: HTTP " + response.statusCode());
        }
    }
}
