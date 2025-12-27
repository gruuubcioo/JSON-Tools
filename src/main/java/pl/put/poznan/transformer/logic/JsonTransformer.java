package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public class JsonTransformer {
    private final String[] transforms;
    private final List<String> filterKeys;

    private final JsonMinifyService minifyService = new JsonMinifyService();
    private final JsonFilterService filterService = new JsonFilterService();
    private final JsonBeautifyService beautifyService = new JsonBeautifyService();


    public JsonTransformer(String[] transforms, List<String> filterKeys) {
        this.transforms = transforms;
        this.filterKeys = filterKeys;
    }

    public String transform(JsonNode body) {
        if (body == null || body.isNull() || body.isMissingNode()) {
            throw new IllegalArgumentException("JSON body is empty or null.");
        }

        for (String transformation : transforms) {
            if (transformation.equalsIgnoreCase("minify")) {
                minifyService.minify(body);
            } else if (transformation.equalsIgnoreCase("filter")) {
                filterService.deleteKeys(body, filterKeys, new java.util.ArrayList<>());
            } else if (transformation.equalsIgnoreCase("beautify")) {
//                poprawic funkcje transform zeby obslugiwala wszysktie 3 rzeczy
            } else {
                throw new UnsupportedOperationException("Operation " + transformation + " is NOT supported.");
            }
        }
        return body.toString();
    }
}
