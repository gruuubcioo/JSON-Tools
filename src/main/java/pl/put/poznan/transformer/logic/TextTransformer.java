package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;

public class TextTransformer {

    private final String[] transforms;

    public TextTransformer(String[] transforms) {
        this.transforms = transforms;
    }

    public JsonNode transform(JsonNode body) {
        return body;
    }
}
