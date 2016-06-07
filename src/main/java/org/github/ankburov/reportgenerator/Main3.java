package org.github.ankburov.reportgenerator;

import org.springframework.util.StringUtils;
import wagu.Block;
import wagu.Board;
import wagu.Table;

import java.io.*;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

public class Main3 {
    public static void main(String[] args) {
        int[][] multiplyTab = new int[10][10];

        /*try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("123.txt")));
            PrintStream printStream = new PrintStream("123.txt");
            System.setOut(printStream);

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                multiplyTab[i][j] = (i+1)*(j+1);
                //вывод ряда чисел разделенных знаком табуляции
                System.out.printf("%4d", multiplyTab[i][j]);
            }
            System.out.println();
        }*/

        System.out.printf("%s%n", "Hello"); // --> "Hello"


        System.out.printf("| %-8s |", "hello");
        System.out.printf(" %-7s |", "10/02/2015");
        System.out.printf(" %-7s |", "Karpov");

//        String s = String.format("Курс валют: %-4s%-8.4f  %-4s%-8.4f","USD", 58.4643, "EUR", 63.3695);
//        System.out.println(s);

        System.out.println();

        Object[] ints = {3, 4, 5, 6, 7};


        System.out.println(String.format("| %-8s |", "hello"));


        List<String> headersList = Arrays.asList("NAME", "GENDER", "MARRIED", "AGE", "SALARY($)");
        List<List<String>> rowsList = Arrays.asList(
                Arrays.asList("Eddy", "Male", "No", "23", "1200.27"),
                Arrays.asList("Libby", "Male", "No", "17", "800.50"),
                Arrays.asList("Rea", "Female", "No", "30", "10000.00"),
                Arrays.asList("Deandre", "Female", "No", "19", "18000.50"),
                Arrays.asList("Alice", "Male", "Yes", "29", "580.40"),
                Arrays.asList("Alyse", "Female", "No", "26", "7000.89"),
                Arrays.asList("Venessa", "Female", "No", "22", "100700.50")
        );
//bookmark 1
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colWidthsListEdited = Arrays.asList(24, 13, 6, 13, 13);
        table.setGridMode(Table.GRID_FULL).setColWidthsList(colWidthsListEdited);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_TOP_LEFT,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);


    }
}
