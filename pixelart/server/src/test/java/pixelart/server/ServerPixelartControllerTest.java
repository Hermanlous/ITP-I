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

	private final String testFilePath = System.getProperty("user.dir") +
			"/src/main/java/resources/persistence/jsonCanvas.json";
	private final int pixelSize = 10;
	private final int gridWidth = 100;
	private final int gridHeight = 100;
	private final Grid grid = new Grid(gridWidth, gridHeight, pixelSize);
	private final String[][] testGrid = grid.getJsonGrid();

	@BeforeEach
	void setUp() throws Exception {
		new File(testFilePath).getParentFile().mkdirs();

		objectMapper.writeValue(new File(testFilePath), testGrid);
	}

	@Test
	void getPixelart_ShouldReturnSuccessMessage() throws Exception {
		mockMvc.perform(get("/run"))
				.andExpect(status().isOk())
				.andExpect(content().string("Pixelart is running"));
	}

	@Test
	void getCanvas_WhenFileExists_ShouldReturnCanvas() throws Exception {
		mockMvc.perform(get("/canvas"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(testGrid)));
	}

	@Test
	void postCanvas_WithValidData_ShouldUpdateAndReturnCanvas() throws Exception {

		mockMvc.perform(put("/canvas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testGrid)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(testGrid)));

		String[][] savedGrid = objectMapper.readValue(new File(testFilePath), String[][].class);
		assertArrayEquals(testGrid, savedGrid);
	}

	@Test
	void postCanvas_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
		String invalidJson = "{ invalid: json }";

		mockMvc.perform(put("/canvas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testCrossOriginSupport() throws Exception {
		mockMvc.perform(options("/canvas")
						.header("Access-Control-Request-Method", "GET")
						.header("Origin", "http://localhost:3000"))
				.andExpect(status().isOk())
				.andExpect(header().exists("Access-Control-Allow-Origin"))
				.andExpect(header().exists("Access-Control-Allow-Methods"));
	}
}