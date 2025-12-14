package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {

    private final String[] transforms;

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
    }

    public JsonNode transform(JsonNode body){
        // of course, normally it would do something based on the transforms
        return body;
    }
}
