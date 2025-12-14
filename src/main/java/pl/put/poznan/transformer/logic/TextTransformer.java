package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TextTransformer {

    private final String[] transforms;
    private final ObjectMapper mapper;

    public TextTransformer(String[] transforms) {
        this.transforms = transforms;
        this.mapper = new ObjectMapper();
    }

    private JsonNode minify(JsonNode json) {
        try {
            return mapper.readTree(json.toString());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean comparison(JsonNode json1, JsonNode json2) {
        System.out.println("comparison");
        return true;
    }

    private JsonNode filtering(JsonNode json) {
        System.out.println("filtering");
        return json;
    }

    public JsonNode transform(JsonNode body) {
        JsonNode result = null;
        for (String transformation : transforms) {
            switch (transformation.toLowerCase()) {
                case "minify":
                    result = minify(body);
                    break;
                case "comparison":
//                    result = comparison(body);
                    System.out.println("comparison");
                    break;
                case "filtering":
                    result = filtering(body);
                    break;
                default:
                    System.out.println("Wrong operation.");
                    break;
            }
        }
        return result;
    }
}
