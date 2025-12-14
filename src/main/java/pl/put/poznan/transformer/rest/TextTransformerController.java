package pl.put.poznan.transformer.rest;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;


@RestController
@RequestMapping("/transform/json")
public class TextTransformerController {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public JsonNode post(@RequestParam(value="transforms")String[] transforms,
                         @RequestBody JsonNode body) {

        TextTransformer transformer = new TextTransformer(transforms);
        return transformer.transform(body);
    }



}


