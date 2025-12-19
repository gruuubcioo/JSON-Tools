package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonComparatorService {
    public List<Integer> compareJsonLineByLine(JsonNode firstJson, JsonNode secondJson) {
        List<Integer> differenceOccurred = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String ChangeFirstJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(firstJson);
            String ChangeSecondJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(secondJson);

            String[] FirstLines = ChangeFirstJson.split("\\r?\\n");
            String[] SecondLines = ChangeSecondJson.split("\\r?\\n");

            int Line1 = FirstLines.length;
            int Line2 = SecondLines.length;
            int MaxLine = Math.max(Line1, Line2);

            for (int i = 0; i < MaxLine; i++) {
                if (i >= FirstLines.length || i >= SecondLines.length) {
                    differenceOccurred.add(i);
                    continue;
                }
                if (!FirstLines[i].equals(SecondLines[i])) {
                    differenceOccurred.add(i);
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return differenceOccurred;
    }
}
