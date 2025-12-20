package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class JsonFilterService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String filterJson(String jsonInput, List<String> keys){
        try{

            JsonNode initNode = objectMapper.readTree(jsonInput);
            JsonNode filteredNode = deleteKeys(initNode, keys);
            return filteredNode.toPrettyString();
        }catch (Exception e){
            throw new IllegalArgumentException("Error: Przesłany tekst nie jest w formacie JSON");
        }
    }
    public JsonNode deleteKeys(JsonNode node, List<String> keys){
        if(node.isObject()){
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.remove(keys);
        }
        //Rekurencja
        node.forEach(child -> deleteKeys(child,keys));
        return node;
    }
}
