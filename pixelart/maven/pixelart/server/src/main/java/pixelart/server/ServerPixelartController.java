package pixelart.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerPixelartController {

    /**
     * Path to the file storing the canvas data.
     */
    //Chatgpt and Claude was used here for correct filepath. Since downgraded spring-boot.
    private final String filePath =
            System.getProperty("user.dir")
                    + "/src/main/resources/persistence/jsonCanvas.json";

    /**
     * ObjectMapper for serializing and deserializing JSON data.
     */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Endpoint to check if the Pixelart server is running.
     *
     * @return A ResponseEntity with a message confirming
     * that the Pixelart server is running.
     */
    @GetMapping("run")
    public ResponseEntity<String> getPixelart() {
        return ResponseEntity.ok("Pixelart is running");
    }

    /**
     * Retrieves the current canvas data from the stored file.
     *
     * @return A ResponseEntity containing the canvas data as a 2D
     * array of strings, or an error status if the file is not found
     * or an IOException occurs.
     */
    @GetMapping(value = "/canvas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String[][]> getCanvas() {
        File file = new File(filePath);
        try {
            //Claude was used here for file not found. From here:
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new String[][] {{"File not found: " + filePath}});
            }//To here.
            String[][] canvasGrid = mapper.readValue(file, String[][].class);
            return ResponseEntity.ok(canvasGrid);
        } catch (IOException e) {
            e.printStackTrace();
            return
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to post a new canvas and update the stored canvas data.
     *
     * @param currentGrid The new canvas data to be saved,
     * passed as a 2D array of strings.
     * @return A ResponseEntity containing the updated
     * canvas data. If an error occurs while saving the
     * data, an INTERNAL_SERVER_ERROR status is returned.
     */
    @PutMapping(
            value = "/canvas",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String[][]> putCanvas(
        @RequestBody final String[][] currentGrid) {

        File file = new File(filePath);

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, currentGrid);

            return ResponseEntity.ok(currentGrid);
        } catch (Exception e) {
            e.printStackTrace();
            return
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
