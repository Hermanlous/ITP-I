package pixelart.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pixelart.service.CanvasResponse;


import java.io.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ServerPixelartController {

    @GetMapping("run")
    public String getPixelart() {
        return "Pixelart API is running";
    }

    public String jsonToString() throws IOException {
        String filepathJson = "C:/Users/Eier/Documents/Høst2024/ITPREBASE/gr2452/pixelart/server/src/main/java/pixelart/persistence/jsonCanvas.json";
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepathJson))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new IOException("File not found: " + filepathJson);
        }
        return content.toString();
    }
    @GetMapping("/canvas")
    public CanvasResponse getUsers() throws IOException {
        String jsonContent = jsonToString();

        return new CanvasResponse( jsonContent );

    }
    /*ChatGPT prompt from 41 to 53 */
    @PutMapping("/canvas")
    public ResponseEntity<String> putCanvas(@RequestBody String canvasUpdate) throws IOException {
        String filepathJson = "./pixelart/server/src/main/java/pixelart/persistence/jsonCanvas.json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathJson))) {
            writer.write(canvasUpdate);
            return ResponseEntity.ok(HttpStatus.OK.toString());

        }catch(IOException error){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed"+error.getMessage());
        }
    }
}
