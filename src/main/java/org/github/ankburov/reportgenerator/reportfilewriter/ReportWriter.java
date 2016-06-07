package org.github.ankburov.reportgenerator.reportfilewriter;

import org.github.ankburov.reportgenerator.settingshandler.Column;

import java.io.*;
import java.util.*;

/**
 * Implementation of AbstractReportWriter
 */
public class ReportWriter extends AbstractReportWriter {
    private BufferedWriter bufferedWriter;
    private String header;
    private String delimiter;
    private int rowCount;   // pagination count

    public ReportWriter() {
        this.rowCount = 0;
    }

    /**
     * Method that generates report and writes it in the file
     *
     * @param list two-dimensional list. Represents source report file in memory. Each List<String> is a row
     */
    @Override
    public void generateReport(List<List<String>> list) {
        try {
            if (!reportFile.exists()) {
                reportFile.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile, true), "UTF-16"));
            generateHeader(settingsReader.getColumnList());
            generateDelimiter(header);
            // write header into report file
            bufferedWriter.append(header);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            rowCount++;
            // iterate list (source report). Each List<String> strings is a row
            for (List<String> strings : list) {
                // write row to the report file
                printTableRow(header, strings, settingsReader.getColumnList());
            }
            // write in log that writing is successful
            logger.info("Writing or report file is successful");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Method that generates header and stores it in as a class field "header"
     *
     * @param columns list of columns
     */
    private void generateHeader(List<Column> columns) {
        StringBuilder headerBuilder = new StringBuilder();
        List<String> headerArgs = new ArrayList<>();
        for (Column column : columns) {
            // add to header format
            headerBuilder.append("| %-");
            headerBuilder.append(column.getWidth());
            headerBuilder.append("s ");
            // add an argument to header list arg
            headerArgs.add(column.getTitle());
        }
        headerBuilder.append("|");
        header = String.format(headerBuilder.toString(), headerArgs.toArray());
        if (header.length() > settingsReader.getPageWidth()) {
            throw new RuntimeException("Summary column length " + header.length() + " " +
                    "(with boundaries) is longer than page width");
        }
    }

    /**
     * Method that generates delimiter from header's length and stores it in as a class field "delimiter"
     */
    private void generateDelimiter(String header) {
        StringBuilder delimiterBuilder = new StringBuilder();
        for (int i = 0; i < header.length(); i++) {
            delimiterBuilder.append("-");
        }
        delimiter = delimiterBuilder.toString();
    }

    /**
     * Method writes row to the report file
     *
     * @param header  header field
     * @param strings list that represents row of table
     * @param columns list of columns
     */
    private void printTableRow(String header, List<String> strings, List<Column> columns) throws IOException {
        //print delimiter
        bufferedWriter.append(delimiter);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        rowCount++;
        // list with formatted row
        List<List<String>> rowList = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            // get formatted row
            rowList.add(getFormattedStrings(new ArrayList<>(), strings.get(i), columns.get(i).getWidth()));
        }
        // find longest column in a row
        List<String> longestString = findLongestString(rowList);
        // write formatted row to the report file
        for (int i = 0; i < longestString.size(); i++) {
            StringBuilder stringFormatBuilder = new StringBuilder();
            List<String> stringFormatArgs = new ArrayList<>();
            for (Column column : columns) {
                // add to header format
                stringFormatBuilder.append("| %-");
                stringFormatBuilder.append(column.getWidth());
                stringFormatBuilder.append("s ");
                // add an argument to header argument list
                for (List<String> stringList : rowList) {
                    stringFormatArgs.add(getString((stringList), i));

                }
            }
            stringFormatBuilder.append("|"); // right boundary of a row
            rowCount++;
            // check current page height exceeded set page height
            if (rowCount > settingsReader.getPageHeight()) {
                bufferedWriter.append("~");
                bufferedWriter.newLine();
                bufferedWriter.append(header);
                bufferedWriter.newLine();
                bufferedWriter.append(delimiter);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                rowCount = 2; // 2 because of header and delimiter
            }
            bufferedWriter.append(String.format(stringFormatBuilder.toString(), stringFormatArgs.toArray()));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    /**
     * Method finds longest column in a row
     *
     * @param rowList list with formatted row
     * @return longest column in a row
     */
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

    /**
     * Method returns string of list. If list doesn't contain this element, method returns ""
     *
     * @param list list with column content in a row
     * @param i    index of element
     * @return String with content of the list's element or ""
     */
    private String getString(List<String> list, int i) {
        String string;
        string = "";
        try {
            string = list.get(i);
        } catch (IndexOutOfBoundsException ignored) {
        }
        return string;
    }

    /**
     * Method formats input row and returns formatted (with column length breakdown) row
     *
     * @param strings         non-formatted raw row
     * @param string          content of column
     * @param maxColumnLength maximum length of this column
     * @return formatted row
     */
    private List<String> getFormattedStrings(List<String> strings, String string, int maxColumnLength) {
        // calculate actual row height with formatted content
        int actualRowHeight = (int) Math.ceil((float) string.length() / (float) maxColumnLength);
        for (int i = 0; i < actualRowHeight; i++) {
            if (string.length() > maxColumnLength - 1) {
                strings.add(string.substring(0, maxColumnLength));
            } else {
                strings.add(string.substring(0, string.length()));
            }
            // chop parsed length from string
            StringBuilder nameBuilder = new StringBuilder(string);
            for (int j = 0; j < maxColumnLength; j++) {
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
