package ru.larina;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


class ExpressionAnalyzeTest {

    @Test
    void calculateSimpleAddition() {
        String str = "2+5";
        Assertions.assertEquals(7, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateSimpleSubtraction() {
        String str = "5-2";
        Assertions.assertEquals(3, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateSimpleMultiplication() {
        String str = "5*2";
        Assertions.assertEquals(10, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateSimpleDivide() {
        String str = "15/3";
        Assertions.assertEquals(5, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateAdditionOfSeveralNumbers() {
        String str = "12+4+34+100";
        Assertions.assertEquals(150, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateSubtractionOfSeveralNumbers() {
        String str = "350-13-15-23";
        Assertions.assertEquals(299, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateMultiplicationOfSeveralNumbers() {
        String str = "12*5*9";
        Assertions.assertEquals(540, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateDivideOfSeveralNumbers() {
        String str = "150/5/6/5";
        Assertions.assertEquals(1, ExpressionAnalyze.calculate(str));
    }

    @Test
    void calculateDivideByZero() {
        String str = "15/(1-1)";
        Assertions.assertThrows(ArithmeticException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ExpressionAnalyze.calculate(str);
            }
        });
    }

    @Test
    void calculateAppliedSymbols() {
        String str = "3+c";
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ExpressionAnalyze.calculate(str);
            }
        });


    }

    @Test
    void calculateTest() {
        String str = "(3457*234)-3421+(4589-1)/2";
        Assertions.assertEquals(807811, ExpressionAnalyze.calculate(str));
    }


}