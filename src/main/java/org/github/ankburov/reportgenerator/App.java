package org.github.ankburov.reportgenerator;

import org.github.ankburov.reportgenerator.reportfilewriter.AbstractReportWriter;
import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.github.ankburov.reportgenerator.sourcereporthandler.AbstractSourceReportReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;


//todo попробовать проверить через схему проверщик хмлник
/** Class with main programm sequence*/
public class App {
    private File settingsFile;
    private File sourceReport;
    @Autowired
    private AbstractSettingsReader settingsReader;
    @Autowired
    private AbstractSourceReportReader reportReader;
    @Autowired
    private AbstractReportWriter reportWriter;

    public App() {
    }

    public void setSettingsFile(File settingsFile) {
        this.settingsFile = settingsFile;
    }

    public void setSourceReport(File sourceReport) {
        this.sourceReport = sourceReport;
    }

    public void doProgram() throws IOException, SAXException, ParserConfigurationException {
        settingsReader.setSettingsFile(settingsFile);
        settingsReader.readSettings();
        System.out.println(settingsReader.toString());

        reportReader.setSourceFile(sourceReport);
        List<List<String>> list = reportReader.readSourceReport();
        System.out.println(list);

        reportWriter.generateReport(list);
    }
}
