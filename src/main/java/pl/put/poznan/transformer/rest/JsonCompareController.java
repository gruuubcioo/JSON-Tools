package pl.put.poznan.transformer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonComparatorService;
import pl.put.poznan.transformer.logic.JsonComparer;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/compare/json")
public class JsonCompareController {

    private final JsonComparatorService jsonComparatorService;

    @Autowired
    public JsonCompareController(JsonComparatorService jsonComparatorService) {
        this.jsonComparatorService = jsonComparatorService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public List<Integer> post(@RequestBody JsonComparer compare) {

        return jsonComparatorService.compareJsonLineByLine(compare.getFirstJson(), compare.getSecondJson());
    }
}
