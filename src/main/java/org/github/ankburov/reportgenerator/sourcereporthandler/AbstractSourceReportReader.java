package org.github.ankburov.reportgenerator.sourcereporthandler;

import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abstract class for reading source file and loading its content into memory
 */
public abstract class AbstractSourceReportReader {
    protected static final Logger logger = LoggerFactory.getLogger("root");
    protected File sourceFile;
    @Autowired
    protected AbstractSettingsReader settingsReader;

    public AbstractSourceReportReader() {
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * read source report
     *
     * @return two-dimensional list. Represents source report file in memory. Each List<String> is a row
     */
    public abstract List<List<String>> readSourceReport() throws IOException;
}
