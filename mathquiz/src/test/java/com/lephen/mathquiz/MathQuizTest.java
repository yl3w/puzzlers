package com.lephen.mathquiz;

import org.junit.Assert;
import org.junit.Test;

public class MathQuizTest {

  @Test
  public void testBasic() {
    MathQuiz mq = new MathQuiz(new int[] { 7, 4 }, 5, 3, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "5+7/4=3");
  }

  @Test
  public void testNoSolution() {
    MathQuiz mq = new MathQuiz(new int[] { 1 }, 1, 2000, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertFalse(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "NO EXPRESSION");
  }

  @Test
  public void testAdvanced() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "12+2-5-1/2=4");
  }

  @Test
  public void testNegativeInput() { // this example is invalid according to
                                    // specification
    MathQuiz mq = new MathQuiz(new int[] { 12, 5, -1, 2 }, 2, 4, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "2+12-5+-1/2=4");
  }

  @Test
  public void testWithLimitedOperators() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0],
        new Operator[] { Operator.SUBSTRACTION, Operator.ADDITION });
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "12-2-5+1-2=4");
  }

  @Test
  public void testWithoutAddition() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0],
        new Operator[] { Operator.SUBSTRACTION, Operator.MULTIPLICATION,
            Operator.DIVISION });
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "12-2/5*1*2=4");
  }

  @Test
  public void testWithoutSubstraction() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0],
        new Operator[] { Operator.ADDITION, Operator.MULTIPLICATION,
            Operator.DIVISION });
    MathQuiz solution = mq.solve();
    Assert.assertFalse(solution.isSatisfied());
  }

  // / TESTING OPERATOR EXTENSIBILITY
  public class Power extends Operator {

    Power() {
      super('^', (a, b) -> {
        if (b < 0)
          throw new UnsupportedOperationException(
              "Cannot perform powers to negative numbers");
        if (b == 0)
          return 1;

        return (int) Math.pow(a, b);
      });
    }
  }

  @Test
  public void testCustomOperators() {
    MathQuiz mq = new MathQuiz(new int[] { 2 }, 3, 9, new char[0],
        new Operator[] { Operator.ADDITION, Operator.SUBSTRACTION,
            Operator.MULTIPLICATION, Operator.DIVISION, new Power() });
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "3^2=9");
  }

  @Test
  public void testRealLongInput() {
    MathQuiz mq = new MathQuiz(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2,
        3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6,
        7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
        1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4,
        5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8 }, 0, 9, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert
        .assertEquals(
            solution.print(),
            "0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1+2+3+4+5+6+7+8+9+0+1/2+3+4/5/6/7+8=9");
  }
}
