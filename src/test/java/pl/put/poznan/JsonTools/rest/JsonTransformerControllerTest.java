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
        ResultActions result = mockMvc.perform(post("/transform/json")
                .param("transforms", "minify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));

        // THEN
        result.andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    // --- Invalid data test ---
    @Test
    void testInvalidJsonShouldReturnBadRequest() throws Exception {
        // GIVEN
        String invalidJson = "{name: name";

        // WHEN
        ResultActions result = mockMvc.perform(post("/transform/json")
                        .param("transforms", "minify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson));

        // THEN
        result.andExpect(status().isBadRequest());
    }
}