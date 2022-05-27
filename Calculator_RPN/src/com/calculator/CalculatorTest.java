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
    void calculate1() {
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
    void testCalculate4() {
        String mathExpression = "( 4 + 2 ) / 3";
        double result = calculator.calculate(mathExpression);
        assertEquals(2.0,result);
    }

    @Test
    void testCalculate5() {
        String mathExpression = "10 + 67 * 3 + 225 / 5";
        double result = calculator.calculate(mathExpression);
        assertEquals(256.0,result);
    }

    @Test
    void testConvertListToRpnList() {
        Calculator calculator = new Calculator();
        List<String> expression = new ArrayList<>();
        expression.add("(");
        expression.add("3");
        expression.add("+");
        expression.add("5");
        expression.add(")");
        expression.add("*");
        expression.add("(");
        expression.add("7");
        expression.add("-");
        expression.add("3");
        expression.add(")");

        List<String> expected = new ArrayList<>();
        expected.add("3");
        expected.add("5");
        expected.add("+");
        expected.add("7");
        expected.add("3");
        expected.add("-");
        expected.add("*");

        List<String> result = calculator.convertListToRpnList(expression);

        assertEquals(expected,result);
    }


    @Test
    void  testAfterCalledConvertMethodCalculateRpnList1() {

        List<String> expression = new ArrayList<>();
        expression.add("3");
        expression.add("5");
        expression.add("+");
        expression.add("7");
        expression.add("3");
        expression.add("-");
        expression.add("*");

        double expected = 32;
        double result = calculator.calculateRpnList(expression);
        assertEquals(expected,result);
    }

    @Test
    void  testAfterCalledConvertMethodCalculateRpnList2() {

        // 4 * 3 + 20
        List<String> expression = new ArrayList<>();
        expression.add("4");
        expression.add("3");
        expression.add("*");
        expression.add("20");
        expression.add("+");


        double expected = 32;
        double result = calculator.calculateRpnList(expression);
        assertEquals(expected,result);
    }


    @Test
    void testAfterCalledConvertMethodCalculateRpnList3() {

        List<String> expression = new ArrayList<>();
        expression.add("3");
        expression.add("5");
        expression.add("+");
        expression.add("3");
        expression.add("7");
        expression.add("-");
        expression.add("*");

        double expected = -32;
        double result = calculator.calculateRpnList(expression);
        assertEquals(expected,result);
    }


}