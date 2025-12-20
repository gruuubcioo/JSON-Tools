package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonFilterService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Metoda dla #15, przyjmuje tekst jako String i liste pól do usunięcia
    public String filterJson(String jsonInput, List<String> keys){
        try{
            //
            JsonNode initNode = objectMapper.readTree(jsonInput);

            //Inicjacja logiki usuwania niechcianych pól
            JsonNode filteredNode = processNode(initNode, keys);
            return filteredNode.toPrettyString();
        }catch (Exception e){
            throw new IllegalArgumentException("Error: Przesłany tekst nie jest w formacie JSON");
        }
    }
    public JsonNode processNode(JsonNode node, List<String> keys){
        
        return node;
    }
}
