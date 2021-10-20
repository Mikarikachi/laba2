package ru.larina;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        System.out.println(ExpressionAnalyze.calculate(expression));
    }
}
