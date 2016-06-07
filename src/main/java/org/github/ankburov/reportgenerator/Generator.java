package org.github.ankburov.reportgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Generator {
    private static final Logger logger = LoggerFactory.getLogger("root");

    public static void main(String[] args) {
        // help
        if (args[0].equals("-?") || args[0].equals("/?") || args[0].equals("help")) {
            System.out.println("settings.xml source-data.csv report.txt");
            System.exit(-1);
        }
        //check args
        if (args.length != 3) {
            System.out.println("Wrong number of arguments. Should be 3. See -? argument");
            System.exit(-1);
        }
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
            App app = (App) context.getBean("app");
            app.setSettingsFile(new File(args[0]));
            app.setSourceReport(new File(args[1]));
            app.setReportFile(new File(args[2]));
            app.doProgram();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
