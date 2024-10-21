package pixelart.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pixelart.service.CanvasResponse;


import java.io.*;


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
    @GetMapping("canvas")
    public CanvasResponse getUsers() throws IOException {
        String jsonContent = jsonToString();

        return new CanvasResponse( jsonContent );

    }
}
