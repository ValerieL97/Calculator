package com.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {


    public double calculate(String mathExpression) {
        String preparedMathExpression = prepareExpression(mathExpression);
        // convert expression into a list of strings
        List<String> elementsFromExpression = convertStringToList(preparedMathExpression);
        // calculate all expressions from parenthesis
        List<String> expressionWithoutParanthesis = calculateParenthesisExpression(elementsFromExpression);
        // calculate the result of expression
        double result = applySubtractionAndAddition(expressionWithoutParanthesis);
        return result;
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

    // extract the expression from parenthesis, resolve it and replace the expression with its result
    // list = {"1", "+", "2", "*", "(", "1", "+", "3" ")"} => returnedList = {"1", "+", "2", "*", "4"}
    public List<String> calculateParenthesisExpression(List<String> mathExpressionElements) {
        List<String> expressionWithoutParanthesis = new ArrayList<>();
        List<String> elementsInMainParanthesis = new ArrayList<>();

        boolean isItInMainParanthesis = false;
        int paranthesisPosition = 0;
        for(String element : mathExpressionElements) {

            if (element.equals("(")) {

                isItInMainParanthesis = true;
                paranthesisPosition++;

            } else if (element.equals(")") && paranthesisPosition == 1) {
                    // it calls the applySubtractionAndAddition method to resolve
                    //the expression from parenthesis
                    double result = applySubtractionAndAddition(elementsInMainParanthesis);
                    elementsInMainParanthesis.clear();
                    expressionWithoutParanthesis.add(Double.toString(result));
                    paranthesisPosition = 0;
                    isItInMainParanthesis = false;

            } else if (!isItInMainParanthesis) {
                expressionWithoutParanthesis.add(element);
            } else {
               elementsInMainParanthesis.add(element);
            }

        }

        return expressionWithoutParanthesis;
    }


    // calculate all division and multiplication operations and replace them with their result
    // list = {"1", "+", "2", "*", "4"} => returnedList = {"1", "+", "8"}
    public List<String> applyDivisionAndMultiplication(List<String> elements){

        List<String> elementsWithoutMultiAndDiv = new ArrayList<>();
        boolean isLastOperatorMultiOrDiv = false;

        int position = 0;

        for(String element : elements) {
            if(element.equals("*")) {
                double result = Double.parseDouble(elements.get(position-1))
                        * Double.parseDouble(elements.get(position+1));
                elementsWithoutMultiAndDiv.add(Double.toString(result));
                isLastOperatorMultiOrDiv = true;
            } else if (element.equals("/")) {
                double result = Double.parseDouble(elements.get(position-1))
                        / Double.parseDouble(elements.get(position+1));
                elementsWithoutMultiAndDiv.add(Double.toString(result));
                isLastOperatorMultiOrDiv = true;
            } else if (element.equals("+") || element.equals("-")) {
                if(!isLastOperatorMultiOrDiv) {
                    elementsWithoutMultiAndDiv.add(elements.get(position - 1));
                }
                elementsWithoutMultiAndDiv.add(element);
                isLastOperatorMultiOrDiv = false;
            } else if (position == elements.size()-1) {
                elementsWithoutMultiAndDiv.add(elements.get(position));
            }

            position++;
        }

        return elementsWithoutMultiAndDiv;
    }

    // calculate all subtraction and addition operations and return the result of expression
    // list = {"1", "+", "8"} => result = 9
    public double applySubtractionAndAddition(List<String> elements) {

        // it calls the applyDivisionAndMultiplication method to resolve all multiplications and
        List<String> subList = applyDivisionAndMultiplication(elements);

        double result = Double.parseDouble(subList.get(0));
        int position = 0;
        for(String element : subList) {
            if(element.equals("+")) {
                result = result + Double.parseDouble(subList.get(position + 1));
            }
            if(element.equals("-")) {
                result = result - Double.parseDouble(subList.get(position + 1));
            }

            position ++;
        }
        return result;
    }


}
