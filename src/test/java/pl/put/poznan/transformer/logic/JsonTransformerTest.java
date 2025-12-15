package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class JsonTransformerTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testMinify() throws JsonProcessingException {
        // GIVEN
        String jsonString = "{    \n" +
                "      \"name\": \"Jan\",\n" +
                "  \"age\":   30,\n" +
                "  \"city\":        \"Poznan\"\n" +
                "}";

        JsonNode jsonNode = mapper.readTree(jsonString);

        String[] transforms = {"minify"};
        JsonTransformer transformer = new JsonTransformer(transforms);

        // WHEN
        JsonNode result = transformer.transform(jsonNode);

        // THEN
        assertNotNull(result);
        assertEquals(jsonNode, result);
    }
}