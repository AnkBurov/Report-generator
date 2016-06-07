package org.github.ankburov.reportgenerator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
        App app = (App) context.getBean("app");
        //todo настроить импорт файлов
        app.setSettingsFile(new File("settings.xml"));
        app.setSourceReport(new File("source-data.csv"));
        //todo сделать обработку Exceptions
        app.doProgram();
    }
}
