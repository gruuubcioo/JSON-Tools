package pl.put.poznan.JsonTools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonComparatorServiceTest {

    private JsonComparatorService jsonComparatorService;
    private ObjectMapper mapper;


    @BeforeEach
    void setUp() {
        this.jsonComparatorService = new JsonComparatorService();
        this.mapper = new ObjectMapper();
    }

    // --- test the same jsons
    @Test
    void testCompareJsonLineByLineSameJsons() throws JsonProcessingException {
        // GIVEN
        String jsonFirstString = """
                        {
                            "name": "Jan",
                            "city": "Poznan"
                        }
                """;
        JsonNode firstJson = mapper.readTree(jsonFirstString);

        String jsonSecondString = """
                        {
                            "name": "Jan",
                            "city": "Poznan"
                        }
                """;
        JsonNode secondJson = mapper.readTree(jsonSecondString);

        // WHEN
        List<Integer> result = jsonComparatorService.compareJsonLineByLine(firstJson, secondJson);

        // THEN
        assertTrue(result.isEmpty());
    }

    // --- test the same jsons
    @Test
    void testCompareJsonLineByLineEmptyJsons() throws JsonProcessingException {
        // GIVEN
        String jsonFirstString = "";
        JsonNode firstJson = mapper.readTree(jsonFirstString);

        String jsonSecondString = "";
        JsonNode secondJson = mapper.readTree(jsonSecondString);

        // WHEN
        List<Integer> result = jsonComparatorService.compareJsonLineByLine(firstJson, secondJson);

        // THEN
        assertTrue(result.isEmpty());
    }

    // --- test different jsons
    @Test
    void testCompareJsonLineByLineDifferentJsons() throws JsonProcessingException {
        // GIVEN
        String jsonFirstString = """
                        {
                            "name": "Jan",
                            "city": "Poznan"
                        }
                """;
        JsonNode firstJson = mapper.readTree(jsonFirstString);

        String jsonSecondString = """
                        {
                            "name": "Maciej"
                        }
                """;
        JsonNode secondJson = mapper.readTree(jsonSecondString);

        // WHEN
        List<Integer> expectedResults = List.of(0, 1);

        // THEN
        assertEquals(expectedResults, jsonComparatorService.compareJsonLineByLine(firstJson, secondJson));
    }
}