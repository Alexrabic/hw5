package org.example;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    double calculate(String expr) {
        Stack<Double> stack = new Stack<>();

        var tokens = translate(expr);
        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                var b = stack.pop();
                var a = stack.pop();
                stack.push(applyOperator(a, b, token));
            } else if (isFunction(token)) {
                if (token.equals("pow")) {
                    var exponent = stack.pop();
                    var base = stack.pop();
                    stack.push(Math.pow(base, exponent));
                } else {
                    stack.push(applyFunction(token, stack.pop()));
                }
            }
        }

        return stack.pop();
    }

    private static List<String> translate(String inputString) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        var inputStringFormatted = inputString.replaceAll(",", " ")
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ");
        var input = inputStringFormatted.split(" ");

        for (String cur : input) {
            if (isNumber(cur)) {
                output.add(cur);
            } else if (isFunction(cur)) {
                stack.push(cur);
            } else if (cur.equals("(")) {
                stack.push(cur);
            } else if (cur.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
                if (!stack.isEmpty() && isFunction(stack.peek())) {
                    output.add(stack.pop());
                }
            } else if (isOperator(cur)) {
                while (!stack.isEmpty() && isOperator(stack.peek()) && getPrecedence(stack.peek()) >= getPrecedence(cur)) {
                    output.add(stack.pop());
                }
                stack.push(cur);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }


    private static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("sqrt") || token.equals("pow");
    }

    private static int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    private static double applyOperator(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private static double applyFunction(String function, double arg) {
        switch (function) {
            case "sin":
                return Math.sin(arg);
            case "cos":
                return Math.cos(arg);
            case "sqrt":
                return Math.sqrt(arg);
            default:
                throw new IllegalArgumentException("Unknown function: " + function);
        }
    }
}
