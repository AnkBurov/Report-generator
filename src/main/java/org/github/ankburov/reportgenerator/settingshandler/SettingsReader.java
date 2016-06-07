package org.github.ankburov.reportgenerator.settingshandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementation of AbstractSettingsReader
 */
public class SettingsReader extends AbstractSettingsReader {

    public SettingsReader() {
        columnList = new ArrayList<>();
    }

    /**
     * validate scheme of XML-file
     */
    private void validateScheme(Document document) throws SAXException, IOException {
        Schema schema = null;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        schema = factory.newSchema(new File("settings.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    /**
     * Method for reading (parsing) settings using Java DOM Parser and adding them to a memory
     */
    @Override
    public void readSettings() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(settingsFile);
        //validate XML scheme
        validateScheme(document);
        Element root = document.getDocumentElement();
        // seek for page tag
        NodeList pageTag = root.getElementsByTagName("page");
        // get child nodes and iterate them
        NodeList pageChildTag = pageTag.item(0).getChildNodes();
        for (int i = 0; i < pageChildTag.getLength(); i++) {
            if (pageChildTag.item(i).getNodeName().equals("width")) {
                pageWidth = Integer.parseInt(pageChildTag.item(i).getTextContent());
            } else if (pageChildTag.item(i).getNodeName().equals("height")) {
                pageHeight = Integer.parseInt(pageChildTag.item(i).getTextContent());
            }
        }
        // seek for columns tag list
        NodeList columns = root.getElementsByTagName("columns");
        // parse columns
        NodeList columnTag = columns.item(0).getChildNodes();
        for (int i = 0; i < columnTag.getLength(); i++) {
            if (columnTag.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String columnTitle = "";
                int columnWidth = 0;
                NodeList columnTagChild = columnTag.item(i).getChildNodes();
                for (int j = 0; j < columnTagChild.getLength(); j++) {
                    if (columnTagChild.item(j).getNodeName().equals("title")) {
                        columnTitle = columnTagChild.item(j).getTextContent();
                    }
                    if (columnTagChild.item(j).getNodeName().equals("width")) {
                        columnWidth = Integer.parseInt(columnTagChild.item(j).getTextContent());
                    }
                }
                // create new Column and add it to column list
                columnList.add(new Column(columnTitle, columnWidth));
            }
        }
        logger.info("Page width is " + pageWidth + " and page height is " + pageHeight);
        logger.info("Number of expected columns is " + columnList.size());
    }
}
