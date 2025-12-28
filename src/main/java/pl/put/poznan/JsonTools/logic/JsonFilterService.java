    package pl.put.poznan.JsonTools.logic;

    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.node.ObjectNode;

    import java.util.ArrayList;
    import java.util.List;

    public class JsonFilterService {
        private final ObjectMapper objectMapper = new ObjectMapper();

        public String filterJson(String jsonInput, List<String> keys){
            List<String> validKeys = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            for(String key : keys){
                if(key != null && !key.trim().isEmpty()){
                    validKeys.add(key);
                }
                else{
                    errors.add("Warning: Wykryto pusty klucz.");
                }
            }
            try{
                List<String> foundKeys = new ArrayList<>();

                JsonNode initNode = objectMapper.readTree(jsonInput);

                JsonNode filteredNode = deleteKeys(initNode, validKeys, foundKeys);
                for(String key : validKeys){
                    if(!foundKeys.contains(key)){
                        errors.add("Warning: Klucz " + key + " nie został wykryty.");
                    }
                }

                return filteredNode.toPrettyString();
            }catch (Exception e){
                throw new IllegalArgumentException("Error: Przesłany tekst nie jest w formacie JSON");
            }
        }
        public JsonNode deleteKeys(JsonNode node, List<String> keys, List<String> foundKeys){
            if(node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                for(String key : keys){
                    if(objectNode.has(key)){
                        objectNode.remove(key);
                        foundKeys.add(key);
                    }
                }
            }
            //Rekurencja
            node.forEach(child -> deleteKeys(child,keys,foundKeys));
            return node;
        }
    }
