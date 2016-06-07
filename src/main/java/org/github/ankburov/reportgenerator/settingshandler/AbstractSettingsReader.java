package org.github.ankburov.reportgenerator.settingshandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abstract class for reading settings
 */
public abstract class AbstractSettingsReader {
    protected static final Logger logger = LoggerFactory.getLogger("root");
    protected File settingsFile;
    protected List<Column> columnList;
    protected int pageWidth;
    protected int pageHeight;

    public AbstractSettingsReader() {
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setSettingsFile(File settingsFile) {
        this.settingsFile = settingsFile;
    }

    /**
     * Method for reading (parsing) settings using Java DOM Parser and adding them to a memory
     */
    public abstract void readSettings() throws ParserConfigurationException, IOException, SAXException;
}
