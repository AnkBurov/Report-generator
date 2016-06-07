package org.github.ankburov.reportgenerator.sourcereporthandler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReportReader extends AbstractSourceReportReader {

    @Override
    public List<List<String>> readSourceReport() throws IOException {
        List<List<String>> list = new ArrayList<>();
        CSVParser parser = CSVParser.parse(sourceFile, StandardCharsets.UTF_16, CSVFormat.TDF);
        List<CSVRecord> records = parser.getRecords();
        for (CSVRecord csvRecord : records) {
            List<String> strings = new ArrayList<>();

            //todo улучшить
            // разбор внутренней и наполнение внешней коллекции
            for (int i = 0; i < settingsReader.getColumnList().size(); i++) {
                String string = csvRecord.get(i);
                strings.add(string);
            }
            list.add(strings);
        }
        return list;
    }
}
