package catering.persistence;

import java.sql.*;
import java.util.*;

public class ResultPrinter implements ResultHandler{

    public ResultPrinter(String tableName){
        this.tableName = tableName;
    }

    private String tableName;

    public void handle(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            int[] columnWidths = new int[columnsNumber];
            List<String[]> rows = new ArrayList<>();

            do {
                String[] row = new String[columnsNumber];
                for (int i = 0; i < columnsNumber; i++) {
                    String value = rs.getString(i + 1);
                    row[i] = value != null ? value : "NULL";
                    columnWidths[i] = Math.max(columnWidths[i], row[i].length());
                    columnWidths[i] = Math.max(columnWidths[i], rsmd.getColumnName(i + 1).length());
                }
                rows.add(row);
            } while (rs.next());

            int totalWidth = Arrays.stream(columnWidths).sum() + (2 * columnsNumber); 

            printCenteredTitle(tableName, totalWidth);
        

            for (int i = 0; i < columnsNumber; i++) {
                System.out.print((padRight(rsmd.getColumnName(i + 1), columnWidths[i]) + "  ").toUpperCase());
            }
            System.out.println();
            for (String[] row : rows) {
                for (int i = 0; i < columnsNumber; i++) {
                    System.out.print(padRight(row[i], columnWidths[i]) + "  ");
                }
                System.out.println();
            }
            printEndTable(totalWidth);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String padRight(String s, int n) {
        if (n <= 0) n = 1;
        if (s == null) s = "NULL";
        return String.format("%-" + n + "s", s);
    }

    private void printCenteredTitle(String title, int width) {
        String upperTitle = title.toUpperCase();
        int dashCount = Math.max(0, (width - upperTitle.length() - 2) / 2);
        String dashes = "-".repeat(dashCount);
        System.out.println(dashes + " " + upperTitle + " " + dashes);
    }
    
    private void printEndTable(int width) {
        String dashes = "-".repeat(width);
        System.out.println(dashes);
    }
}

