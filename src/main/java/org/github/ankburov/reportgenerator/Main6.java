package org.github.ankburov.reportgenerator;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Main6 {
    private static int MAX_ROW_LENGTH = 32;
    private static int MAX_HEIGHT = 12;
    private static int MAX_NUMBER_LENGTH = 8;
    private static int MAX_DATE_LENGTH = 7;
    private static int MAX_NAME_LENGTH = 7;
    private static int ROW_COUNT = 0;

    public static void main(String[] args) {


        String header = String.format("| %-" + MAX_NUMBER_LENGTH + "s | %-" + MAX_DATE_LENGTH + "s | " +
                "%-" + MAX_NAME_LENGTH + "s |", "Номер", "Дата", "ФИО");
        printHeader(header);

        String number = "1";
        String date = "25/11";
        String name = "123456789";

        printTableRow(header, number, date, name);
        printTableRow(header, "2", "5/10", "Karpov");
        printTableRow(header, "3", "5/10", "Ivan Komarov");
        printTableRow(header, "4", "5/10", "Karpov");
        printDelimiter(header);
    }

    private static List<String> getFormattedStrings(List<String> strings, String string, int MAX_NUMBER) {
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

    private static void printHeader(String header) {
        if (header.length() > MAX_ROW_LENGTH) {
            throw new RuntimeException("Не помещается");
        }
        System.out.println(header);


    }

    private static void printDelimiter(String header) {
        StringBuilder delimiter = new StringBuilder();
        for (int i = 0; i < header.length(); i++) {
            delimiter.append("-");
        }
        System.out.println(delimiter.toString());
    }

    //todo теоретически через коллекцию можно сделать универсальной возможно
    private static void printTableRow(String header, String number, String date, String name) {
        printDelimiter(header);

        List<String> subNumber = new ArrayList<>();
        subNumber = getFormattedStrings(subNumber, number, MAX_NUMBER_LENGTH);


        List<String> subDate = new ArrayList<>();
        subDate = getFormattedStrings(subDate, date, MAX_DATE_LENGTH);

        List<String> subName = new ArrayList<>();
        subName = getFormattedStrings(subName, name, MAX_NAME_LENGTH);

        //////////////////////////////////////
        for (int i = 0; i < subNumber.size() || i < subDate.size() || i < subName.size(); i++) {
            number = getString(subNumber, i);

            date = getString(subDate, i);

            name = getString(subName, i);

            String row = String.format("| %-" + MAX_NUMBER_LENGTH + "s | %-" + MAX_DATE_LENGTH + "s | " +
                    "%-" + MAX_NAME_LENGTH + "s |", number, date, name);
            System.out.println(row);

            // increment row counter
            ROW_COUNT++;
            if (ROW_COUNT > MAX_HEIGHT) {
                System.out.println("~");
                printHeader(header);
                ROW_COUNT = 0;
            }

        }
    }

    private static String getString(List<String> list, int i) {
        String string;
        string = "";
        try {
            string = list.get(i);
        } catch (IndexOutOfBoundsException ignored) {
        }
        return string;
    }
}
