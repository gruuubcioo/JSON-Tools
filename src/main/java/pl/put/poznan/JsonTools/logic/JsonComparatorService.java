package pl.put.poznan.JsonTools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Serwis odpowiedzialny za logikę porównywania struktur JSON.
 * <p>
 * Klasa udostępnia funkcjonalność porównywania dwóch obiektów JSON linia po linii,
 * z pominięciem znaków strukturalnych takich jak nawiasy klamrowe.
 */
@Service
public class JsonComparatorService {

    /**
     * Porównuje dwa obiekty JsonNode linia po linii.
     * <p>
     * Metoda formatuje oba obiekty do postaci "Pretty Print" (czytelnej dla człowieka),
     * a następnie porównuje je wierszami. Linie zawierające jedynie klamry strukturalne
     * są ignorowane podczas porównania.
     *
     * @param firstJson  pierwszy obiekt JSON do porównania (jako JsonNode)
     * @param secondJson drugi obiekt JSON do porównania (jako JsonNode)
     * @return lista numerów linii (indeksowana od 0), w których wykryto różnice między obiektami.
     * Jeśli jedna struktura jest dłuższa od drugiej, nadmiarowe linie również są traktowane jako różnica.
     * @throws RuntimeException jeśli wystąpi błąd podczas przetwarzania JSON (JsonProcessingException)
     */
    public List<Integer> compareJsonLineByLine(JsonNode firstJson, JsonNode secondJson) {
        List<Integer> differenceOccurred = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String ChangeFirstJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(firstJson);
            String ChangeSecondJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(secondJson);

            String[] FirstLines = ChangeFirstJson.split("\\r?\\n");
            String[] SecondLines = ChangeSecondJson.split("\\r?\\n");

            FirstLines = cleanArray(FirstLines);
            SecondLines = cleanArray(SecondLines);

            int Line1 = FirstLines.length;
            int Line2 = SecondLines.length;
            int MaxLine = Math.max(Line1, Line2);

            for (int i = 0; i < MaxLine; i++) {
                if (i >= FirstLines.length || i >= SecondLines.length) {
                    differenceOccurred.add(i);
                    continue;
                } else if (!FirstLines[i].equals(SecondLines[i])) {
                    differenceOccurred.add(i);
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return differenceOccurred;
    }

    /**
     * Metoda pomocnicza filtrująca tablicę linii tekstu.
     * <p>
     * Usuwa z tablicy wiersze zawierające wyłącznie znaki strukturalne JSON,
     * takie jak pojedyncze klamry otwierające "{" lub zamykające "}", "},".
     *
     * @param lines tablica ciągów znaków (linii) do przefiltrowania
     * @return nowa tablica ciągów znaków pozbawiona linii zawierających tylko klamry
     */
    private String[] cleanArray(String[] lines) {
        return Arrays.stream(lines)
                .filter(line -> {
                    String t = line.trim();
                    return !(t.equals("{") || t.equals("}") || t.equals("},"));
                })
                .toArray(String[]::new);
    }
}
