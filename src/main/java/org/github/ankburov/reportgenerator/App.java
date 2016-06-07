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

/**
 * Class with main program sequence
 */
public class App {
    private File settingsFile;
    private File sourceReport;
    private File reportFile;
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

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
    }

    public void doProgram() throws IOException, SAXException, ParserConfigurationException {
        settingsReader.setSettingsFile(settingsFile);
        settingsReader.readSettings();

        reportReader.setSourceFile(sourceReport);
        List<List<String>> list = reportReader.readSourceReport();

        reportWriter.setReportFile(reportFile);
        reportWriter.generateReport(list);
    }
}
