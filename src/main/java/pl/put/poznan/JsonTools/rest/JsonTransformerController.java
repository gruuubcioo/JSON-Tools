package pl.put.poznan.JsonTools.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.JsonTools.logic.JsonTransformer;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/transform/json")
public class JsonTransformerController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/plain")
    public String post(@RequestParam(value = "transforms") String[] transforms, @RequestParam(value = "keys", required = false) List<String> keys, @RequestBody JsonNode body) throws JsonProcessingException {

        JsonTransformer transformer = new JsonTransformer(transforms, keys);
        return transformer.transform(body);
    }
}


