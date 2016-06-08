# Report generator #
Program reads xml file with settings, checks its content with XSD (settings.xsd) and from source csv (tab-delimited) file generates report file.

## Installation ##
* Using Maven compile and install project
* Copy to destination folder settings.xsd file with XML-scheme
* Copy to destination folder tab-delimited source file and settings.xml
* Execute command: java -jar reportgenerator-1.0-SNAPSHOT.jar settings.xml source-data.csv report.txt