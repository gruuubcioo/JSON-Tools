package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;

@RestController
@RequestMapping("/transform/json")
public class TextTransformerController {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public JsonNode post(@RequestParam(value = "transforms") String[] transforms, @RequestBody JsonNode body) {

        TextTransformer transformer = new TextTransformer(transforms);
        return transformer.transform(body);
    }
}


