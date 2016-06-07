import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws IOException {
        File csvData = new File("source-data.csv");
        CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_16, CSVFormat.TDF);
//        for (CSVRecord csvRecord : parser) {
//            System.out.println(csvRecord);
//            for (String s : csvRecord) {
//                System.out.println(s);
//            }
//        }
        List<CSVRecord> records = parser.getRecords();
        for (CSVRecord record : records) {
            for (int i = 0; i < 3; i++) {
                System.out.println(record.get(i));
            }
        }
    }
}
