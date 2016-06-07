package org.github.ankburov.reportgenerator.reportfilewriter;

import org.github.ankburov.reportgenerator.settingshandler.Column;
import org.github.ankburov.reportgenerator.sourcereporthandler.AbstractSourceReportReader;

import java.util.*;

/**
 * Implementation of AbstractReportWriter
 */
public class ReportWriter extends AbstractReportWriter {
    private String header;
    private String delimiter;
    private int rowCount;

    public ReportWriter() {
        this.rowCount = 0;
    }

    /**
     * Method that generates report
     */
    @Override
    public void generateReport(List<List<String>> list) {
        generateHeader(settingsReader.getColumnList());
        generateDelimiter(header);

        System.out.println(header);
        rowCount++;

        for (List<String> strings : list) {
            printTableRow(header, strings, settingsReader.getColumnList());
        }

    }

    /**
     * Method that generates header and stores it in as a class field "header"
     */
    private void generateHeader(List<Column> columns) {
        StringBuilder headerBuilder = new StringBuilder();
        List<String> headerArgs = new ArrayList<>();
        for (Column column : columns) {
            // add to header format
            headerBuilder.append("| %-");
            headerBuilder.append(column.getWidth());
            headerBuilder.append("s ");
            // add an argument to header array
            headerArgs.add(column.getTitle());
        }
        headerBuilder.append("|");
        header = String.format(headerBuilder.toString(), headerArgs.toArray());
        if (header.length() > settingsReader.getPageWidth()) {
            throw new RuntimeException("Не помещается");
        }
    }

    /** Method that generates delimiter from header's length and stores it in as a class field "delimiter"*/
    private void generateDelimiter(String header) {
        StringBuilder delimiterBuilder = new StringBuilder();
        for (int i = 0; i < header.length(); i++) {
            delimiterBuilder.append("-");
        }
        delimiter = delimiterBuilder.toString();
    }

    private void printTableRow(String header, List<String> strings, List<Column> columns) {
        //print delimiter
        System.out.println(delimiter);
        rowCount++;

        // лист строчек
        List<List<String>> rowList = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            rowList.add(getFormattedStrings(new ArrayList<>(), strings.get(i), columns.get(i).getWidth()));
        }

//        System.out.println(rowList);

        //max
        List<String> longestString = findLongestString(rowList);

        // print
        for (int i = 0; i < longestString.size(); i++) {
            StringBuilder stringFormatBuilder = new StringBuilder();
            List<String> stringFormatArgs = new ArrayList<>();
            for (Column column : columns) {
                // add to header format
                stringFormatBuilder.append("| %-");
                stringFormatBuilder.append(column.getWidth());
                stringFormatBuilder.append("s ");
                // add an argument to header array
//                stringFormatArgs.add(column.getTitle());

                //todo индекс для роулиста?
                for (List<String> stringList : rowList) {
                    stringFormatArgs.add(getString((stringList), i));

                }

            }
            stringFormatBuilder.append("|");

            rowCount++;
            if (rowCount > settingsReader.getPageHeight()) {
                System.out.println("~");
                System.out.println(header);
                System.out.println(delimiter);
                rowCount = 2;
            }

            System.out.println(String.format(stringFormatBuilder.toString(), stringFormatArgs.toArray()));
        }



        /*for (int i = 0; i < longestString.size(); i++) {
            String number = getString(rowList.get(0), i);

            String date = getString(rowList.get(1), i);

            String name = getString(rowList.get(2), i);

            String row = String.format("| %-" + columns.get(0).getWidth() + "s | %-" + columns.get(0).getWidth() + "s | " +
                    "%-" + columns.get(2).getWidth() + "s |", number, date, name);
            System.out.println(row);
        }*/

        /*for (int i = 0; i < rowList.get(0).size() || i <  rowList.get(1).size() || i <  rowList.get(2).size(); i++) {
            String number = getString(rowList.get(0), i);

            String date = getString(rowList.get(1), i);

            String name = getString(rowList.get(2), i);

            String row = String.format("| %-" + columns.get(0).getWidth() + "s | %-" + columns.get(0).getWidth() + "s | " +
                    "%-" + columns.get(2).getWidth() + "s |", number, date, name);
            System.out.println(row);

            // increment row counter
//            ROW_COUNT++;
//            if (ROW_COUNT > MAX_HEIGHT) {
//                System.out.println("~");
//                printHeader(header);
//                ROW_COUNT = 0;
//            }

        }*/
    }

    private List<String> findLongestString(List<List<String>> rowList) {
        return Collections.max(rowList, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                if (o1.size() > o2.size()) {
                    return 1;
                } else if (o1.size() < o2.size()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    private String getString(List<String> list, int i) {
        String string;
        string = "";
        try {
            string = list.get(i);
        } catch (IndexOutOfBoundsException ignored) {
        }
        return string;
    }

    private List<String> getFormattedStrings(List<String> strings, String string, int MAX_NUMBER) {
        int arrayLength = (int) Math.ceil((float) string.length() / (float) MAX_NUMBER);
        for (int i = 0; i < arrayLength; i++) {
            if (string.length() > MAX_NUMBER - 1) {
                strings.add(string.substring(0, MAX_NUMBER));
            } else {
                strings.add(string.substring(0, string.length()));
            }

            StringBuilder nameBuilder = new StringBuilder(string);
            for (int j = 0; j < MAX_NUMBER; j++) {
                if (nameBuilder.length() > 1) {
                    nameBuilder.deleteCharAt(0);
                } else {
                    break;
                }
            }

            string = nameBuilder.toString();
        }
        return strings;
    }
}
