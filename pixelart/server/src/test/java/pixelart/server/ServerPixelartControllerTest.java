package pixelart.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pixelart.core.Grid;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ServerPixelartController.class)
class ServerPixelartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private final String correctFilePath = System.getProperty("user.dir") +
			"/src/main/resources/persistence/jsonCanvas.json";

	private final String testFilePath = System.getProperty("user.dir") +
			"/src/main/resources/persistence/jsonCanvas.json";

	private final int pixelSize = 10;
	private final int gridWidth = 60;
	private final int gridHeight = 34;
	private final Grid grid = new Grid(gridHeight, gridWidth, pixelSize);
	private final String[][] testGrid = grid.getJsonGrid();

	@BeforeEach
	void setUp() throws Exception {
		new File(correctFilePath).getParentFile().mkdirs();

		objectMapper.writeValue(new File(correctFilePath), testGrid);
	}

	@Test
	void returnPixelart() throws Exception {
		mockMvc.perform(get("/run"))
				.andExpect(status().isOk())
				.andExpect(content().string("Pixelart is running"));
	}

	@Test
	void returnCanvasWithValidFile() throws Exception {
		mockMvc.perform(get("/canvas"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(testGrid)));
	}

	@Test
	void postCanvasWithValidData() throws Exception {

		mockMvc.perform(put("/canvas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testGrid)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(testGrid)));

		String[][] savedGrid = objectMapper.readValue(new File(correctFilePath), String[][].class);
		assertArrayEquals(testGrid, savedGrid);
	}

	@Test
	void postCanvasWithInvalidJSON() throws Exception {
		String invalidJson = "{ invalid: json }";

		mockMvc.perform(put("/canvas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void returnCanvasWithNoFile() throws Exception {
		new File(testFilePath).delete();

		mockMvc.perform(get("/canvas"))
				.andExpect(status().isNotFound())
				.andExpect(content().json(objectMapper.writeValueAsString(
						new String[][] {{"File not found: " + testFilePath}}
				)));
	}

	/*@Test
	void testCrossOriginSupport() throws Exception {
		mockMvc.perform(options("/canvas")
						.header("Access-Control-Request-Method", "GET")
						.header("Origin", "http://localhost:3000"))
				.andExpect(status().isOk())
				.andExpect(header().exists("Access-Control-Allow-Origin"))
				.andExpect(header().exists("Access-Control-Allow-Methods"));
	}*/
}