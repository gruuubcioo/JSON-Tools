    package pl.put.poznan.transformer.logic;

    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.node.ObjectNode;

    import java.util.ArrayList;
    import java.util.List;

    public class JsonFilterService {
        private final ObjectMapper objectMapper = new ObjectMapper();

        public String filterJson(String jsonInput, List<String> keys){
            try{
                List<String> validKeys = new ArrayList<>();
                for(String key : keys){
                    if(key != null && !key.trim().isEmpty()){
                        validKeys.add(key);
                    }
                    else{
                        //Informuj ze wykryto bledny klucz (do doknczenia przy #18)
                    }
                }
                List<String> foundKeys = new ArrayList<>();

                JsonNode initNode = objectMapper.readTree(jsonInput);

                JsonNode filteredNode = deleteKeys(initNode, keys, foundKeys);
                for(String key : validKeys){
                    if(!foundKeys.contains(key)){
                        //Informuj ze klucz nie zostal znaleziony (do doknczenia przy #18)
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
