package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.List;

public class JsonBeautifyService {
    private final JsonMinifyService minifyService = new JsonMinifyService();

    public void beautify(JsonNode node) {
        JsonNode testNode = node.deepCopy();
        minifyService.minify(testNode);

        if (!node.equals(testNode)) {
            throw new IllegalArgumentException("Podany JSON nie był zminifikowany.");
        }

        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;

            List<String> fieldNames = new ArrayList<>();
            objectNode.fieldNames().forEachRemaining(fieldNames::add);

            for (String fieldName : fieldNames) {
                JsonNode value = objectNode.get(fieldName);

                beautify(value);

                if (value.isTextual()) {
                    String trimmedValue = value.asText().trim();
                    if (!value.asText().equals(trimmedValue)) {
                        value = new TextNode(trimmedValue);
                        objectNode.replace(fieldName, value);
                    }
                }

                String trimmedKey = fieldName.trim();
                if (!fieldName.equals(trimmedKey)) {
                    objectNode.remove(fieldName);
                    objectNode.set(trimmedKey, value);
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;

            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode element = arrayNode.get(i);

                if (element.isTextual()) {
                    arrayNode.set(i, new TextNode(element.asText().trim()));
                } else {
                    beautify(element);
                }
            }
        }
    }
}
