package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;

@RestController
@RequestMapping("/api/v1/transform")
public class TextTransformerController {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public JsonNode post(@RequestBody JsonNode body, @RequestParam(value="transforms") String[] transforms) {

        TextTransformer transformer = new TextTransformer(transforms);
        return transformer.transform(body);
    }
}


