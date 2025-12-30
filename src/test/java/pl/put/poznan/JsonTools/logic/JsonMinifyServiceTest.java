package pl.put.poznan.JsonTools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class JsonMinifyServiceTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testMinify() throws JsonProcessingException {
        // GIVEN
        String jsonString = "{    \n" +
                "      \"name\": \"  Jan\",\n" +
                "  \"age\":   30,\n" +
                "  \"city   \":        \"Miasto   Poznan\"\n" +
                "}";

        JsonNode jsonNode = mapper.readTree(jsonString);

        String[] transforms = {"minify"};
        JsonTransformer transformer = new JsonTransformer(transforms, null);

        String expectedJson = "{\"name\":\"Jan\",\"age\":30,\"city\":\"Miasto Poznan\"}";

        // WHEN
        String result = transformer.transform(jsonNode);

        // THEN
        assertNotNull(result);
        assertEquals(expectedJson, result);
    }
    @Test
    void testMinifyEmpty() throws JsonProcessingException {
        String jsonString = "{}";

        JsonNode jsonNode = mapper.readTree(jsonString);
        String[] transforms = {"minify"};
        JsonTransformer transformer = new JsonTransformer(transforms, null);

        String result = transformer.transform(jsonNode);
        assertEquals("{}", result);
    }

    @Test
    void testMinifySingleLine() throws JsonProcessingException {
        String jsonString = "{     \"name\" :  \" Tomaszek\" }";

        JsonNode jsonNode = mapper.readTree(jsonString);
        String[] transforms = {"minify"};
        JsonTransformer transformer = new JsonTransformer(transforms, null);

        String result = transformer.transform(jsonNode);
        assertEquals("{\"name\":\"Tomaszek\"}", result);
    }
    @Test
    void testMinifyNestedJson() throws JsonProcessingException {
        String JsonString = " {\"author\": \n" +
                "    {\n" +
                "      \"lastname\": \"Doe\",\n" +
                "      \"firstname\": \"Jane\"\n" +
                "    }\n" +
                "}";
        JsonNode jsonNode = mapper.readTree(JsonString);
        String[] transforms = {"minify"};
        JsonTransformer transformer = new JsonTransformer(transforms, null);

        String result = transformer.transform(jsonNode);
        assertEquals("{\"author\":{\"lastname\":\"Doe\",\"firstname\":\"Jane\"}}",result);
    }
}