package org.github.ankburov.reportgenerator.reportfilewriter;

import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * Abstract class for writing report from source file in defined in settings.xml format
 */
public abstract class AbstractReportWriter {
    @Autowired
    protected AbstractSettingsReader settingsReader;
    protected File reportFile;

    public AbstractReportWriter() {
    }

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
    }

    /**
     * Method that generates report
     */
    public abstract void generateReport(List<List<String>> list);
}
