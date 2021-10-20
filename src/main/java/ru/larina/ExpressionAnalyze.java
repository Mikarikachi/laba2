package ru.larina;

import java.util.*;

public class ExpressionAnalyze {
    public static int calculate(String inputData) {
        isValidExpression(inputData);
        String expression = inputParameters(inputData);
        List<Part> parts = expressionParser(expression);
        PartBuffer partBuffer = new PartBuffer(parts);
        if (!isExpression(partBuffer)) {
            return 0;
        }
        return additionSubtraction(partBuffer);
    }

    /**
     * Check for parameters (x,y) in the received string.
     * The user needs to input the parameters
     *
     * @param inputData  received string
     * @return the expression as a string
     */
    private static String inputParameters(String inputData) {
        Scanner scanner = new Scanner(System.in);
        if (inputData.contains("x")) {
            System.out.println("Please, enter x:");
            inputData = inputData.replaceAll("x", String.valueOf(scanner.nextInt()));
        }
        if (inputData.contains("y")) {
            System.out.println("Please, enter y:");
            inputData = inputData.replaceAll("y", String.valueOf(scanner.nextInt()));
        }
        return inputData;
    }

    /**
     * Split the expression into lexemes.
     *
     * @param inputData  received string
     * @return the expression as a list of lexemes
     */
    private static List<Part> expressionParser(String inputData) {
        List<Part> parts = new ArrayList<>();
        for (int pos = 0; pos < inputData.length(); pos++) {
            char symbol = inputData.charAt(pos);
            switch (symbol) {
                case '(':
                    parts.add(new Part(Type.LEFT_BRACKET, symbol));
                    break;
                case ')':
                    parts.add(new Part(Type.RIGHT_BRACKET, symbol));
                    break;
                case '+':
                    parts.add(new Part(Type.PLUS, symbol));
                    break;
                case '-':
                    parts.add(new Part(Type.MINUS, symbol));
                    break;
                case '*':
                    parts.add(new Part(Type.MULTI, symbol));
                    break;
                case '/':
                    parts.add(new Part(Type.DIV, symbol));
                    break;
                default:
                    if (Character.isDigit(symbol)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(symbol);
                        pos++;

                        while (pos < inputData.length()) {
                            symbol = inputData.charAt(pos);
                            if (Character.isDigit(symbol)) {
                                sb.append(symbol);
                                pos++;
                            } else {
                                break;
                            }

                        }
                        parts.add(new Part(Type.NUMBER, sb.toString()));
                    }
                    pos--;
            }
        }
        parts.add(new Part(Type.END_OF_EXPRESSION, ""));
        return parts;
    }

    /**
     * Check the expression for emptiness.
     *
     * @param parts  of input expression
     * @return true if the expression is not empty, otherwise false
     */
    private static boolean isExpression(PartBuffer parts) {
        Part part = parts.next();
        if (part.type == Type.END_OF_EXPRESSION) {
            return false;
        } else {
            parts.back();
            return true;
        }
    }


    /**
     * The operation with the higher priority is performed,
     * then an addition and subtraction operation is performed.
     *
     * @param parts  of input expression
     * @return expression value
     */
    private static int additionSubtraction(PartBuffer parts) {
        int value = multiplicationDivide(parts);
        while (true) {
            Part part = parts.next();
            switch (part.type) {
                case PLUS:
                    value += multiplicationDivide(parts);
                    break;
                case MINUS:
                    value -= multiplicationDivide(parts);
                    break;
                case END_OF_EXPRESSION:
                case RIGHT_BRACKET:
                    parts.back();
                    return value;
                default:
                    throw new RuntimeException("Неизвестный символ: " + part.value);
            }
        }
    }

    /**
     * The operation with the higher priority is performed,
     * then a multiplication and divide operation is performed.
     *
     * @param parts  of input expression
     * @return expression value
     */
    private static int multiplicationDivide(PartBuffer parts) {
        int value = subexpression(parts);
        while (true) {
            Part part = parts.next();
            switch (part.type) {
                case MULTI:
                    value *= subexpression(parts);
                    break;
                case DIV:
                    int i = subexpression(parts);
                    if (i == 0) {
                        throw new ArithmeticException("Арифметическая ошибка, делить на ноль нельзя!");
                    } else {
                        value /= i;
                    }
                    break;
                case END_OF_EXPRESSION:
                case RIGHT_BRACKET:
                case PLUS:
                case MINUS:
                    parts.back();
                    return value;
                default:
                    throw new RuntimeException("Неизвестный символ: " + part.value);
            }
        }
    }

    /**
     * The expression in parentheses is calculated,
     * then the operations are performed according to priority.
     *
     * @param parts  of input expression
     * @return expression value
     */
    private static int subexpression(PartBuffer parts) {
        Part part = parts.next();
        switch (part.type) {
            case NUMBER:
                return Integer.parseInt(part.value);
            case LEFT_BRACKET:
                int value = additionSubtraction(parts);
                part = parts.next();
                if (part.type != Type.RIGHT_BRACKET) {
                    throw new RuntimeException("Неизвестный символ: " + part.value);
                }
                return value;
            default:
                throw new RuntimeException("Неизвестный символ: " + part.value);
        }
    }

    /**
     * Check for invalid characters in the received string.
     *
     * @param str  received string
     */
    private static void isValidExpression(String str) {
        Character[] symbols = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'x', 'y',
                '+', '-', '*', '/', ' ', '(', ')'};
        Set<Character> appliedSymbols = new HashSet<>(Arrays.asList(symbols));
        for (int i = 0; i<str.length(); i++) {
            if (!appliedSymbols.contains(str.charAt(i))) {
                throw new IllegalArgumentException("Выражение не может содержать введенные символы");
            }
        }
    }
}
