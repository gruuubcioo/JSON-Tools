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
class JsonCompareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Valid data test ---

    @Test
    void testCompareIdenticalJson() throws Exception{
        //GIVEN
        String inputJson = """
            {
            "firstJson": { "id": 1 },
            "secondJson": { "id": 1 }
            }
            """;
        //WHEN
        ResultActions result = mockMvc.perform(post("/api/compare/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testCompareDifferentJson() throws Exception{
        //GIVEN
        String inputJson = """
            {
            "firstJson": { "id": 1 },
            "secondJson": { "id": 2,
                            "name": "Norbert"
                          }
            }
            """;

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/compare/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().json("[0,1]"));
    }

    @Test
    void testCompareJsonArrays() throws Exception{
        //GIVEN
        String inputJson = """
            {
            "firstJson": { "name": ["Norbert","Maciej"] },
            "secondJson": { "name": ["Norbert", "Piotr"] }
            }
            """;

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/compare/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().json("[0]"));
    }

    @Test
    void testCompareNestedJson() throws Exception{
        //GIVEN
        String inputJson = """
            {
            "firstJson": {
                  "student": {
                    "profile": {
                      "settings": {
                        "name": "Norbert"
                      }
                    }
                  }
            },
            "secondJson": {
                  "student": {
                    "profile": {
                      "settings": {
                        "name": "Piotr"
                      }
                    }
                  }
                }
            }
            """;

        //WHEN
        ResultActions result = mockMvc.perform(post("/api/compare/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().json("[3]"));
    }

    @Test
    void testCompareEmptyJsonWithNotEmpty() throws Exception{
        //GIVEN
        String inputJson = """
            {
            "firstJson": {},
            "secondJson": {"id": 1}
            }
            """;
        //WHEN
        ResultActions result = mockMvc.perform(post("/api/compare/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson));
        //THEN
        result.andExpect(status().isOk())
                .andExpect(content().json("[0]"));
    }



}