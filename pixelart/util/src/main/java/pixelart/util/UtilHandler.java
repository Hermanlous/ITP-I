package pixelart.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import pixelart.core.Grid;
import pixelart.core.GridStateHandler;
import pixelart.core.Pixel;

public class UtilHandler {
    private Grid grid;
    private final RestClient restClient = RestClient.create();
    private final GridStateHandler handler;
    private Pixel pixel;

    public UtilHandler(GridStateHandler handler, Grid grid) {
        this.grid = grid;
        this.handler = handler;
    }

    @PostMapping("/canvas")
    public void saveStateToJSON() {
        try {
            String[][] response = grid.getJsonGrid();
            restClient.post()
                    .uri("/canvas")
                    .body(response)
                    .retrieve();
        } catch (RestClientException e) {
            System.err.println("Failed to send grid to API " + e.getMessage());
        }
    }

    @GetMapping("/canvas")
    public void loadStateFromJSON() {
        try {
            String response =
                    restClient.get()
                            .uri("/canvas")
                            .retrieve()
                            .body(String.class);

            JSONArray jsonArray = new JSONArray(response);

            String[][] stringifyJsonArray = new String[jsonArray.length()][];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray row = jsonArray.getJSONArray(i);
                stringifyJsonArray[i] = new String[row.length()];
                for (int j = 0; j < row.length(); j++) {
                    stringifyJsonArray[i][j] = row.getString(j);
                }
            }
            }
        } catch (

        )
    }
}