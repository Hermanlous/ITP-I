package pixelart.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerPixelartController {
    private final String filePath = "persistence/jsonCanvas.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private String[][] canvasData;

    @GetMapping("run")
    public ResponseEntity<String> getPixelart() {

        return ResponseEntity.ok("Pixelart is running");

    }
    //ChatGPT was used to find relative filepath, with Inputstream
    @GetMapping(value = "canvas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String[][]> getCanvas() {
        //From here, to
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new String[][] {{"File not found in classpath: " + filePath}});//here
            }
            String[][] canvasGrid = mapper.readValue(inputStream, String[][].class);
            return ResponseEntity.ok(canvasGrid);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(
            value = "/canvas",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String[][]> postCanvas(@RequestBody String[][] currentGrid) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), currentGrid);

            return ResponseEntity.ok(currentGrid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}