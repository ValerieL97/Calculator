package com.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
    Reverse Polish notation (RPN) is a method for conveying mathematical expressions without
    the use of separators such as brackets and parentheses. In this notation, the operators follow
    their operands, hence removing the need for brackets to define evaluation priority.
    The operation is read from left to right but execution is done every time an operator is reached,
    and always using the last two numbers as the operands. This notation is suited for
    computers and calculators since there are fewer characters to track and fewer operations to execute.
*/

public class Calculator {

    public double calculate(String mathExpression) {
        String preparedMathExpression = prepareExpression(mathExpression);
        List<String> mathExpressionElements = convertStringToList(preparedMathExpression);
        return calculateRpnList(mathExpressionElements);
    }

    // add a zero if the expression starts with a negative number
    // add space between numbers and operators
    public String prepareExpression(String mathExpression) {
        StringBuilder preparedExpression = new StringBuilder();

        for(int i = 0; i < mathExpression.length(); i++) {
            char c = mathExpression.charAt(i);
            if(!Character.isDigit(c)) {
                if (c == '-') {
                    if (i == 0) {
                        preparedExpression.append('0' + " - ");
                    } else if (mathExpression.charAt(i - 1) == '(') {
                        preparedExpression.append(" ").append('0').append(" ");
                    } else {
                        preparedExpression.append(" ").append(c).append(" ");
                    }
                } else {
                    preparedExpression.append(" ").append(c).append(" ");
                }
            } else {
                preparedExpression.append(c);
            }

        }

        return preparedExpression.toString();
    }

    //check if expression contains letters
    //check if last character is an operator like +, - , / , *
    //check if all parentheses are closed
    public boolean isMathExpressionValid(String mathExpression) {
        int repeatedOpenParenthesis = mathExpression.length() -
                mathExpression.replace("(", "").length();
        int repeatedCloseParenthesis = mathExpression.length() -
                mathExpression.replace(")", "").length();
        if(mathExpression.matches(".*[a-zA-Z].*") ||
                (!Character.isDigit(mathExpression.charAt(mathExpression.length()-1))
                        && mathExpression.charAt(mathExpression.length()-1) != ')' ) ||
                repeatedOpenParenthesis != repeatedCloseParenthesis ) {
            return false;
        }

        return true;
    }


    public List<String> convertStringToList(String mathExpression) {
        List<String> elements = new ArrayList<>();

        for(int i = 0; i < mathExpression.length(); i++) {
            char c = mathExpression.charAt(i);
            if (c <= '9' && c >= '0'){
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(c);
                    i++;
                    if (i >= mathExpression.length()) {
                        break;
                    }
                    c = mathExpression.charAt(i);
                } while (c <= '9' && c >= '0');
                elements.add(sb.toString());
            } else if (c == '(' || c == '-' || c == '+' || c == '*' || c == '/' || c == ')'){
                elements.add(Character.toString(c));
            }

        }

        return elements;
    }

    //----------------------------reverse polish notation algorithm-----------------------------//

    public double calculateRpnList(List<String> mathExpressionElements) {

        List<String> rpnExpressionElements = convertListToRpnList(mathExpressionElements);
        Stack<Double> numbers = new Stack<>();

        for(String element : rpnExpressionElements) {

            if(isNumber(element)) {

                numbers.push(Double.parseDouble(element));

            } else if(decideOperationsPriority(element) > 1) {
                Double number1 = numbers.pop();
                Double number2 = numbers.pop();

                numbers.push(applyOperator(number1,number2,element));
            }

        }

        return numbers.pop();
    }


    // return a list with arranged elements as reverse polish notation list
    // the given list = {"(","4","-","3",")","*","2"} -> rpn list = {"4","3","-","2","*"}
    public List<String> convertListToRpnList(List<String> mathExpressionElements) {

        List<String> rpnExpressionList = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for(String element : mathExpressionElements) {
            int priority = decideOperationsPriority(element);
            if(isNumber(element)) {
                rpnExpressionList.add(element);
            } else if(element.equals("(")) {
               operators.push(element);
            } else if(priority > 1) {
                while(!operators.empty() && decideOperationsPriority(operators.peek()) >= priority) {
                    rpnExpressionList.add(operators.pop());
                }
                operators.push(element);
            } else if (element.equals(")")) {
                while(decideOperationsPriority(operators.peek()) != 1) {
                    rpnExpressionList.add(operators.pop());
                }
                operators.pop();
            }
        }

        while(!operators.empty()) {
            rpnExpressionList.add(operators.pop());
        }

        return rpnExpressionList;
    }



    //return the priority of the given operator,
    public static int decideOperationsPriority(String operator) {
        if(operator.equals("/") || operator.equals("*")) {
            return 3;
        }else if(operator.equals("+") || operator.equals("-")) {
            return 2;
        }else if(operator.equals("(")) {
            return 1;
        } else if(operator.equals(")")) {
            return -1;
        } else {
            return 0;
        }
    }

    //check if given element is a number
    public boolean isNumber(String element) {
        try {
            double d = Double.parseDouble(element);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //calculate the operation with the given parameters
    public static double applyOperator(double number1, double number2, String operator) {

        if(operator.equals("+")) {
            return number1 + number2;
        }

        if(operator.equals("-")) {
            return number2 - number1;
        }

        if(operator.equals("*")) {
            return number1 * number2;
        }

        if(operator.equals("/")) {
            return number2 / number1;
        }

        return 0;
    }
}
