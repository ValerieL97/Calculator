package com.calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        String mathExpression = scanner.nextLine();

        if(calculator.isMathExpressionValid(mathExpression)) {
            System.out.println(calculator.calculate(mathExpression));
        } else {
            System.out.println("Invalid expression!");
        }

    }
}
