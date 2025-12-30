package pl.put.poznan.JsonTools.logic;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonBeautifyServiceTest {
    private ObjectMapper mapper;
    private JsonTransformer transformer;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        String[] transforms = {"beautify"};
        transformer = new JsonTransformer(transforms, null);
    }
    @Test
    void testBeautifySimpleObjects() throws JsonProcessingException {
        // GIVEN
        String minifiedJson = "{\"name\":\"Tomasz\",\"test\":\"jsontools\"}}";
        JsonNode inputNode = mapper.readTree(minifiedJson);

        // WHEN
        String result = transformer.transform(inputNode);

        // THEN
        assertTrue(result.contains("\n"));
        assertTrue(result.contains("  "));
    }

    @Test
    void testBeautifyArray() throws JsonProcessingException {
        // GIVEN
        String minifiedJson = "{\"array\":[\"test1\",\"test2\",\"test3\"]}";
        JsonNode inputNode = mapper.readTree(minifiedJson);

        // WHEN
        String result = transformer.transform(inputNode);

        // THEN
        assertTrue(result.contains("[") && result.contains("]"));
    }

    @Test
    void testBeautifyMixedDataTypes() throws JsonProcessingException {
        // GIVEN
        String minifiedJson = "{\"active\":true,\"count\":10,\"data\":null}";
        JsonNode inputNode = mapper.readTree(minifiedJson);

        // WHEN
        String result = transformer.transform(inputNode);

        // THEN
        assertTrue(result.contains("true"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("null"));
    }

    @Test
    void testBeautifyNestedObjects() throws JsonProcessingException {
        // GIVEN
        String minifiedJson = "{\"outer\":{\"inner\":\"value\"}}";
        JsonNode inputNode = mapper.readTree(minifiedJson);
        String expected = "{\n  \"outer\" : {\n    \"inner\" : \"value\"\n  }\n}";

        // WHEN
        String result = transformer.transform(inputNode);

        // THEN
        assertEquals(expected.replace("\r\n", "\n").trim(), result.replace("\r\n", "\n").trim());
    }

}