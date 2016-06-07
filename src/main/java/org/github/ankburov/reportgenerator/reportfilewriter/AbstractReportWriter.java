package org.github.ankburov.reportgenerator.reportfilewriter;

import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * Abstract class for writing report from source file in defined in settings.xml format
 */
public abstract class AbstractReportWriter {
    protected static final Logger logger = LoggerFactory.getLogger("root");
    @Autowired
    protected AbstractSettingsReader settingsReader;
    protected File reportFile;

    public AbstractReportWriter() {
    }

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
    }

    /**
     * Method that generates report and writes it in the file
     *
     * @param list two-dimensional list. Represents source report file in memory. Each List<String> is a row
     */
    public abstract void generateReport(List<List<String>> list);
}
