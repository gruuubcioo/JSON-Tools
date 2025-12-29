package pl.put.poznan.JsonTools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonTransformer {
    private final String[] transforms;
    private final List<String> filterKeys;
    private final ObjectMapper mapper;

    private final JsonMinifyService minifyService = new JsonMinifyService();
    private final JsonFilterService filterService = new JsonFilterService();
    private final JsonBeautifyService beautifyService = new JsonBeautifyService();

    public JsonTransformer(String[] transforms, List<String> filterKeys) {
        this.transforms = transforms;
        this.filterKeys = filterKeys;
        this.mapper = new ObjectMapper();
    }

    public String transform(JsonNode body) throws JsonProcessingException {
        if (body == null || body.isNull() || body.isMissingNode()) {
            throw new IllegalArgumentException("JSON body is empty or null.");
        }

        for (String transformation : transforms) {
            if (transformation.equalsIgnoreCase("minify")) {
                minifyService.minify(body);
                return body.toString();
            } else if (transformation.equalsIgnoreCase("filter")) {
                filterService.deleteKeys(body, filterKeys, new java.util.ArrayList<>());
                return body.toString();
            } else if (transformation.equalsIgnoreCase("beautify")) {
                beautifyService.beautify(body);
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            } else {
                throw new UnsupportedOperationException("Nieznana operacja: " + transformation);
            }
        }

        return body.toString();
    }
}