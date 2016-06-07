import org.github.ankburov.reportgenerator.App;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.charset.Charset;

@ContextConfiguration(classes = org.github.ankburov.reportgenerator.Configuration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {
    @Autowired
    private App app;
    File file = new File("settings.xml");
    File file2 = new File("source-Data.csv");
    File reportFile = new File("testReport.txt");

    @org.junit.Test
    public void testName() throws Exception {
        app.setSettingsFile(file);
        app.setSourceReport(file2);
        app.setReportFile(reportFile);
        app.doProgram();

        BufferedReader bufferedReader = null;
        try {
            int rowCount = 0;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile), "UTF-16"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rowCount++;
            }
            Assert.assertEquals(47, rowCount);
        } finally {
            bufferedReader.close();
        }

    }

    @After
    public void tearDown() throws Exception {
        reportFile.delete();
    }
}
