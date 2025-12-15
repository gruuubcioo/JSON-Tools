package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonTransformer;

@RestController
@RequestMapping("/transform/json")
public class JsonTransformerController {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public JsonNode post(@RequestParam(value = "transforms") String[] transforms, @RequestBody JsonNode body) {

        JsonTransformer transformer = new JsonTransformer(transforms);
        return transformer.transform(body);
    }
}


