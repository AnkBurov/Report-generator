package org.github.ankburov.reportgenerator;

import org.github.ankburov.reportgenerator.reportfilewriter.AbstractReportWriter;
import org.github.ankburov.reportgenerator.reportfilewriter.ReportWriter;
import org.github.ankburov.reportgenerator.settingshandler.AbstractSettingsReader;
import org.github.ankburov.reportgenerator.settingshandler.SettingsReader;
import org.github.ankburov.reportgenerator.sourcereporthandler.AbstractSourceReportReader;
import org.github.ankburov.reportgenerator.sourcereporthandler.ReportReader;
import org.springframework.context.annotation.Bean;

/**
 * Spring annotated application context
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public App app() {
        return new App();
    }

    @Bean
    public AbstractSettingsReader abstractSettingsReader() {
        return new SettingsReader();
    }

    @Bean
    public AbstractSourceReportReader abstractSourceReportReader() {
        return new ReportReader();
    }

    @Bean
    public AbstractReportWriter abstractReportWriter() {
        return new ReportWriter();
    }
}
