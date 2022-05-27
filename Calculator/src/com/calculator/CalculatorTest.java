package com.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    public Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testCalculate1() {

        String mathExpression = "( 5 + 5 )  * ( 4 - 2 )";
        double result = calculator.calculate(mathExpression);
        assertEquals(20.0,result);
    }

    @Test
    void testCalculate2() {

        String mathExpression = "5 + 5 * ( 4 - 2 )";
        double result = calculator.calculate(mathExpression);
        assertEquals(15.0,result);
    }

    @Test
    void testCalculate3() {

        String mathExpression = "10 / ( 4 - 2 )";
        double result = calculator.calculate(mathExpression);
        assertEquals(5.0,result);
    }


    @Test
    void testCalculateParanthesisExpression() {
        List<String> mathExpression = new ArrayList<>();
        mathExpression.add("2");
        mathExpression.add("+");
        mathExpression.add("6");
        mathExpression.add("*");
        mathExpression.add("(");
        mathExpression.add("5");
        mathExpression.add("+");
        mathExpression.add("3");
        mathExpression.add(")");
        mathExpression.add("+");
        mathExpression.add("3");


        List<String> expected= new ArrayList<>();
        expected.add("2");
        expected.add("+");
        expected.add("6");
        expected.add("*");
        expected.add("8.0");
        expected.add("+");
        expected.add("3");

        List<String> result = calculator.calculateParenthesisExpression(mathExpression);

        assertEquals(expected,result);

    }

    @Test
    void testCalculateParanthesisExpressionWithTwoExpressionInParanthesis() {
        List<String> mathExpression = new ArrayList<>();
        mathExpression.add("(");
        mathExpression.add("2");
        mathExpression.add("+");
        mathExpression.add("6");
        mathExpression.add(")");
        mathExpression.add("*");
        mathExpression.add("(");
        mathExpression.add("5");
        mathExpression.add("+");
        mathExpression.add("3");
        mathExpression.add(")");
        mathExpression.add("+");
        mathExpression.add("3");


        List<String> expected= new ArrayList<>();
        expected.add("8.0");
        expected.add("*");
        expected.add("8.0");
        expected.add("+");
        expected.add("3");

        List<String> result = calculator.calculateParenthesisExpression(mathExpression);

        assertEquals(expected,result);

    }

    @Test
    void testApplyDivisionAndMultiplication() {

        List<String> mathExpression = new ArrayList<>();
        mathExpression.add("2");
        mathExpression.add("+");
        mathExpression.add("6");
        mathExpression.add("*");
        mathExpression.add("2");
        mathExpression.add("-");
        mathExpression.add("5");

        List<String> expected = new ArrayList<>();
        expected.add("2");
        expected.add("+");
        expected.add("12.0");
        expected.add("-");
        expected.add("5");

        List<String> result = calculator.applyDivisionAndMultiplication(mathExpression);

        assertEquals(expected,result);
    }

    @Test
    void testApplyDivisionAndMultiplicationWithTwoElements() {

        List<String> mathExpression = new ArrayList<>();
        mathExpression.add("6");
        mathExpression.add("/");
        mathExpression.add("2");


        List<String> expected = new ArrayList<>();
        expected.add("3.0");

        List<String> result = calculator.applyDivisionAndMultiplication(mathExpression);

        assertEquals(expected,result);
    }

    @Test
    void testApplySubtractionAndAddition() {

        List<String> elements = new ArrayList<>();
        elements.add("2");
        elements.add("+");
        elements.add("12.0");
        elements.add("-");
        elements.add("5");

        double result = calculator.applySubtractionAndAddition(elements);
        assertEquals(9,result);
    }

    @Test
    void testApplySubtractionAndAdditionExpressionWithOnlyOneElement() {

        List<String> elements = new ArrayList<>();
        elements.add("20");

        double result = calculator.applySubtractionAndAddition(elements);
        assertEquals(20.0,result);
    }

    @Test
    void testPrepareExpression() {
        String mathExpression = "-1+3*(4-5)+3";
        String expected = "0 - 1 + 3 *  ( 4 - 5 )  + 3";

        String result = calculator.prepareExpression(mathExpression);
        assertEquals(expected,result);

    }

    @Test
    void testPrepareExpression2() {
        String mathExpression = "-12+36*(45-51)+33";
        String expected = "0 - 12 + 36 *  ( 45 - 51 )  + 33";

        String result = calculator.prepareExpression(mathExpression);
        assertEquals(expected,result);

    }

    @Test
    void testConvertStringToList() {
        String mathExpression = "(5 + 5 ) / ( 4 - 2 )";
        //"0 - 1 + 3 *  ( 4 - 5 )  + 3";

        List<String> result = calculator.convertStringToList(mathExpression);
        List<String> expected = new ArrayList<>();
        expected.add("(");
        expected.add("5");
        expected.add("+");
        expected.add("5");
        expected.add(")");
        expected.add("/");
        expected.add("(");
        expected.add("4");
        expected.add("-");
        expected.add("2");
        expected.add(")");

        assertEquals(expected,result);
    }

    @Test
    void testConvertStringToListWithWhiteSpace() {
        String mathExpression = "0 - 1 + 3 *  ( 4 - 5 )  + 3";
        //"0 - 1 + 3 *  ( 4 - 5 )  + 3";

        List<String> result = calculator.convertStringToList(mathExpression);
        List<String> expected = new ArrayList<>();
        expected.add("0");
        expected.add("-");
        expected.add("1");
        expected.add("+");
        expected.add("3");
        expected.add("*");
        expected.add("(");
        expected.add("4");
        expected.add("-");
        expected.add("5");
        expected.add(")");
        expected.add("+");
        expected.add("3");

        assertEquals(expected,result);
    }

    @Test
    void testIsMathExpressionValidStringWithLetters() {
        String mathExpression = "1 + b + 2 + 5";
        assertFalse(calculator.isMathExpressionValid(mathExpression));
    }

    @Test
    void testIsMathExpressionValidOperatorLastCharacter() {
        String mathExpression = "1 + 2 + 5 +";
        assertFalse(calculator.isMathExpressionValid(mathExpression));
    }

    @Test
    void testIsMathExpressionValidWithoutCloseParanthesis() {
        String mathExpression = "1 + 2 + 5 * (4 - 2 + 3";
        assertFalse(calculator.isMathExpressionValid(mathExpression));
    }
}