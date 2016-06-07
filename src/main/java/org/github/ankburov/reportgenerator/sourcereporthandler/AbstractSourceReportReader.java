package org.github.ankburov.reportgenerator.sourcereporthandler;

import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abstract class for reading source file and loading its content into memory
 */
public abstract class AbstractSourceReportReader {
    protected File sourceFile;
    @Autowired
    protected AbstractSettingsReader settingsReader;

    public AbstractSourceReportReader() {
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public abstract List<List<String>> readSourceReport() throws IOException;
}
