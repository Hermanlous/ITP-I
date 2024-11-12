package pixelart.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class ReadJsonTest {

    private GridManager gridManager;
    private int gridSize;

    @BeforeEach
    public void setup() {
        int gridSize = 5;
        int pixelSize = 10;
        this.gridManager = new GridManager(gridSize,pixelSize);
    }

    @AfterEach
    public void tearDown() {
        File file = new File("testJsonCanvas.json");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void successfullyReadJson() {
        String filepathJson = "jsonCanvas.json";
        try {
            String content = gridManager.readJsonFile(filepathJson);
            assertNotNull(content);
        } catch (IOException e) {
            System.out.println("Error retrieving file");
        }
    }

    @Test
    public void testReadJsonFileFileNotFound() {
        String invalidFilepath = "invalidFilePath.json";
        assertThrows(IOException.class, () -> {
            gridManager.readJsonFile(invalidFilepath);
        }, "The file is nonexistent");
    }

    @Test
    public void testLoadJsonFileWithValidFileName() throws IOException {
        String validJsonContent = "{ \"canvas\": [[\"B\", \"W\", \"B\", \"W\", \"B\"], [\"W\", \"B\", \"W\", \"B\", \"W\"], [\"B\", \"W\", \"B\", \"W\", \"B\"], [\"W\", \"B\", \"W\", \"B\", \"W\"], [\"B\", \"W\", \"B\", \"W\", \"B\"]]}";
        GridManager gridManagerSpy = Mockito.spy(gridManager);

        doReturn(validJsonContent).when(gridManagerSpy).readJsonFile(anyString());
        gridManagerSpy.loadState();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                String expectedColor = (row + col) % 2 == 0 ? "B" : "W";
                assertEquals(expectedColor, gridManagerSpy.getJsonGrid()[row].getString(col));
            }
        }

    }

    @Test
    public void testLoadJsonFileOnEmptyFile() throws IOException {
        String emptyTestFile = "testEmptyFileName.json";
        GridManager gridManagerSpy = Mockito.spy(gridManager);

        File file = new File(emptyTestFile);
        for (int row = 0; row < gridSize; row++ ) {
            for (int col = 0; col < gridSize; col++ ) {
                String expectedColor = "W";
                String actualColor = gridManagerSpy.jsonGrid[row].getString(col);
                assertEquals(expectedColor, actualColor);
            }
        }
        file.delete();
    }

    @Test
    public void testLoadJsonFileOnInvalidFile() throws IOException {
        String invalidFilepath = "invalidFilePath.json";
        GridManager gridManagerSpy = Mockito.spy(gridManager);
        doThrow(new FileNotFoundException(invalidFilepath)).when(gridManagerSpy).readJsonFile(anyString());
        gridManagerSpy.loadState();
        doThrow(new IOException()).when(gridManagerSpy).readJsonFile(anyString());
        gridManagerSpy.loadState();
    }
}
