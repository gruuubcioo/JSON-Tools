package pl.put.poznan.JsonTools.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.put.poznan.JsonTools.app.JsonToolsApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.not;

@SpringBootTest(classes = JsonToolsApplication.class)
@AutoConfigureMockMvc
class JsonTransformerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Valid data test ---
    @Test
    void testMinifyShouldReturnMinifiedJson() throws Exception {
        // GIVEN
        String inputJson = """
                {
                  "name": "Jan",
                  "city": "Poznan"
                }""";

        String expectedJson = "{\"name\":\"Jan\",\"city\":\"Poznan\"}";

        // WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "minify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));

        // THEN
        result.andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    // --- Invalid data test ---
    @Test
    void testInvalidJsonShouldReturnBadRequestForMinify() throws Exception {
        // GIVEN
        String invalidJson = "{name: name";

        // WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                        .param("transforms", "minify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson));

        // THEN
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testBeautifyShouldReturnPrettyPrintedJson() throws Exception {
        // GIVEN
        String inputJson = "{\"name\":\"Jan\",\"city\":\"Poznan\"}";
        String expectedSubstring = "\"name\" : \"Jan\"";

        // WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "beautify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));

        // THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(containsString("\n")))
                .andExpect(content().string(containsString(expectedSubstring)));
    }

    @Test
    void testInvalidJsonShouldReturnBadRequestForBeautify() throws Exception {
        // GIVEN
        String prettyInput = "{\n  \"name\": \"Jan\"\n";

        // WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "beautify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(prettyInput));

        // THEN
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testBeautifyNestedStructure() throws Exception {
        // GIVEN
        String input = "{\"user\":{\"id\":1},\"tags\":[\"java\",\"spring\"]}";

        // WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "beautify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        // THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(containsString("  \"user\" : {")))
                .andExpect(content().string(containsString("    \"id\" : 1")));
    }

    @Test
    void testFilterOneKey() throws Exception{
        //GIVEN
        String input = "{\"id\":1,\"name\":\"Norbert\"}";

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "filter")
                .param("keys","id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"id\":1"))))
                .andExpect(content().string(containsString("{\"name\":\"Norbert\"}")));
    }

    @Test
    void testFilterKeysInArray() throws Exception{
        //GIVEN
        String input = "{\"students\":[{\"id\":1, \"name\":\"Kamil\"}, {\"id\":2, \"name\":\"Anna\"}]}";

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "filter")
                .param("keys","name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"name\""))))
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"id\":2")));
    }

    @Test
    void testFilterInvalidKey() throws Exception{
        //GIVEN
        String input = "{\"name\":\"Norbert\"}";

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "filter")
                .param("keys","surname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Norbert\"")));
    }

    @Test
    void testFilterManyKeys() throws Exception{
        //GIVEN
        String input = "{\"id\":1,\"name\":\"Anna\",\"surname\":\"Nowak\",\"age\":45}";

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "filter")
                .param("keys","id","surname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"id\""))))
                .andExpect(content().string(not(containsString("\"surname\""))))
                .andExpect(content().string(containsString("\"name\"")))
                .andExpect(content().string(containsString("\"age\"")));
    }

    @Test
    void testFilterRecurency() throws Exception{
        //GIVEN
        String input = "{\"student\":{\"id\":1,\"name\":\"Mateusz\"},\"major\":\"it\"}";

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/transform/json")
                .param("transforms", "filter")
                .param("keys","name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"name\""))))
                .andExpect(content().string(containsString("\"student\":{")))
                .andExpect(content().string(containsString("\"id\":1")));
    }

}