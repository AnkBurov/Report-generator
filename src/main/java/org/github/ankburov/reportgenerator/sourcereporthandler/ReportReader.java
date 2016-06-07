package org.github.ankburov.reportgenerator.sourcereporthandler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AbstractSourceReportReader. Reads source file and loads its content into memory
 */
public class ReportReader extends AbstractSourceReportReader {
    /**
     * read source report
     *
     * @return two-dimensional list. Represents source report file in memory. Each List<String> is a row
     */
    @Override
    public List<List<String>> readSourceReport() throws IOException {
        List<List<String>> list = new ArrayList<>();
        CSVParser parser = CSVParser.parse(sourceFile, StandardCharsets.UTF_16, CSVFormat.TDF);
        List<CSVRecord> records = parser.getRecords();
        for (CSVRecord csvRecord : records) {
            List<String> strings = new ArrayList<>();
            // form lists
            for (int i = 0; i < settingsReader.getColumnList().size(); i++) {
                String string = csvRecord.get(i);
                strings.add(string);
            }
            list.add(strings);
        }
        return list;
    }
}
