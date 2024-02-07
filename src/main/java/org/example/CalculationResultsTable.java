package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculationResultsTable extends JFrame {
    private JTable table;

    public CalculationResultsTable() {
        setTitle("Results");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String[] columnNames = {"Number", "startX", "endX", "result"};


        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);


        table = new JTable(model);


        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);


        fillTable();

        setVisible(true);
    }

    private void fillTable() {
        Scanner scanner = new Scanner(System.in);
        double startX = 0;
        double endX = 0;
        double num = 0;

        try {
            System.out.print("Введите начальное значение Х: ");
            startX = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка! Введено не число");
            return;
        }

        try {
            System.out.print("Введите конечное значение Х: ");
            endX = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка! Введено не число");
            return;
        }
        if (startX > endX) {
            printErrorRow("Error");
            return;
        }

        System.out.print("Введите num: ");
        num = scanner.nextDouble();
        if (num == 0) {
            printErrorRow("Error, num = 0");
            return;
        }

        System.out.print("Введите шаг изменения переменной Х: ");
        double deltaX = scanner.nextDouble();
        if (deltaX == 0 || deltaX < 0) {
            addTableRow(1, startX, endX, result(startX, num));
            return;
        }
        BigDecimal x = new BigDecimal(Double.toString(startX));
        int rowNumber = 1;
        for (BigDecimal current = x; current.doubleValue() <= endX; current = current.add(new BigDecimal(Double.toString(deltaX)))) {
            double roundedValue = current.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            addTableRow(rowNumber++, roundedValue, endX, result(roundedValue, num));
        }

    }

    private void addTableRow(int rowNumber, double startX, double endX, double result) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{rowNumber, startX, endX, result});
    }

    private void printErrorRow(String error) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{ "1", "", "", error});
    }

    public static double result(double x, double num) {
        double arg = Math.abs(num / 10) + 2;
        return Math.max(
                Math.log(arg) * ((1 - num) / Math.sin(x + num)),
                Math.abs(Math.cos(x) / num)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculationResultsTable());
    }
}
