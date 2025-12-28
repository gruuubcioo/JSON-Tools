package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.ArrayList;
import java.util.List;

public class JsonMinifyService {
    void minify(JsonNode json) {
        if (json.isObject()) {
            ObjectNode objectNode = (ObjectNode) json;

            List<String> list = new ArrayList<>();
            objectNode.fieldNames().forEachRemaining(list::add);

            for (String key : list) {
                JsonNode value = objectNode.get(key);
                String cleanedKey = key.trim().replaceAll("\\s+", " ");

                if (!key.equals(cleanedKey)) {
                    objectNode.remove(key);
                    objectNode.set(cleanedKey, value);
                }

                if (value.isTextual()) {
                    objectNode.put(cleanedKey, value.asText().trim().replaceAll("\\s+", " "));
                }

                minify(objectNode.get(cleanedKey));
            }
        } else if (json.isArray()) {
            ArrayNode arrayNode = (ArrayNode) json;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode element = arrayNode.get(i);

                if (element.isTextual()) {
                    arrayNode.set(i, TextNode.valueOf(element.asText().trim().replaceAll("\\s+", " ")));
                }

                minify(arrayNode.get(i));
            }
        }
    }
}
