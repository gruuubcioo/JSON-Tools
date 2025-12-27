package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonBeautifyServiceTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    void testBeautify() throws JsonProcessingException {
        // GIVEN
        String minifiedJson = "{\"name\":\"Tomasz\",\"details\":{\"city\":\"Poznan\",\"hobby\":\"keyboards\"}}";
        JsonNode inputNode = mapper.readTree(minifiedJson);
        String expectedPrettyJson = """
                {
                  "name" : "Tomasz",
                  "details" : {
                    "city" : "Poznan",
                    "hobby" : "keyboards"
                  }
                }""";

        String[] transforms = {"beautify"};
        JsonTransformer transformer = new JsonTransformer(transforms, null);

        // WHEN
        String result = transformer.transform(minifiedJson);
        // naprawic funkcje transform i przetestowac czy test dziala

        // THEN
        // asercje

    }

}