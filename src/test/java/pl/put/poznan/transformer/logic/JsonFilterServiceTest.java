package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFilterServiceTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    void testSimpleDelete() throws Exception {
        String input = """
               {"id": 1, "imie": "id"}
               """;
        JsonNode jsonNode = mapper.readTree(input);
        List<String> keys = Arrays.asList("id");

        String[] transforms = {"filter"};
        JsonTransformer transformer = new JsonTransformer(transforms, keys);

        String result = transformer.transform(jsonNode);
        JsonNode resultNode = mapper.readTree(result);

        assertFalse(resultNode.has("id"), "Pole 'id' nie zostalo usuniete");
        assertTrue(resultNode.has("imie"), "Pole 'imie' nie pozostalo w strukturze");
        assertEquals("id", resultNode.get("imie").asText(), "Wartosc w polu 'imie' zostala zmieniona");
    }

    @Test
    void testRecurancyDelete() throws Exception {
        String input = """
                {
                  "id": 1,
                  "dane": {
                    "id": "2",
                    "imie": "Jan"
                  }
                }
                """;
        JsonNode jsonNode = mapper.readTree(input);
        List<String> keys = Arrays.asList("id");
        
        String[] transforms = {"filter"};
        JsonTransformer transformer = new JsonTransformer(transforms, keys);

        String result = transformer.transform(jsonNode);
        JsonNode resultNode = mapper.readTree(result);

        assertFalse(resultNode.has("id"), "Pole 'id' z pierwszego poziomu nie zostalo usuniete.");
        assertFalse(resultNode.get("dane").has("id"), "Pole 'id' z drugiego poziomu nie zostalo usuniete");
        assertTrue(resultNode.get("dane").has("imie"), "Pole 'value' zostało usuniete.");
    }

    @Test
    void testInvalidKey() throws Exception {
        String input = """
            {"imie": "Norbert"}
        """;
        JsonNode jsonNode = mapper.readTree(input);
        List<String> keys = Arrays.asList("nazwisko");

        String[] transforms = {"filter"};
        JsonTransformer transformer = new JsonTransformer(transforms, keys);

        String result = transformer.transform(jsonNode);
        JsonNode resultNode = mapper.readTree(result);

        assertTrue(resultNode.has("imie"), "Pole 'imie' zostało usunięte");
        assertEquals(1, resultNode.size(), "Pola ulegly zmianie pomimo nieprawidlowego klucza");
    }

    @Test
    void testEmptyKeys() throws Exception {
        String input = """
                {"id": 1}
        """;
        JsonNode jsonNode = mapper.readTree(input);
        List<String> keys = Arrays.asList(null, "", "  ", "id");

        String[] transforms = {"filter"};
        JsonTransformer transformer = new JsonTransformer(transforms, keys);

        String result = transformer.transform(jsonNode);
        JsonNode resultNode = mapper.readTree(result);

        assertFalse(resultNode.has("id"), "Pole 'id' nie zostalo usuniete.");
    }

    @Test
    void testDeleteInArray() throws Exception {
        String input = """
            {"tablica": [{"id": 1}, {"id": 2}]}
            """;
        JsonNode jsonNode = mapper.readTree(input);
        List<String> keys = Arrays.asList("id");

        String[] transforms = {"filter"};
        JsonTransformer transformer = new JsonTransformer(transforms, keys);

        String result = transformer.transform(jsonNode);
        JsonNode resultNode = mapper.readTree(result);


        assertFalse(resultNode.get("tablica").get(0).has("id"), "Pierwsze pole 'id' tablicy nie zostalo usuniete.");
        assertFalse(resultNode.get("tablica").get(1).has("id"), "Drugie pole 'id' tablicy nie zostalo usuniete.");
    }



}