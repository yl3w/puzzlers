package com.lephen.mathquiz;

import org.junit.Assert;
import org.junit.Test;

import com.lephen.mathquiz.MathQuiz.Operator;

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
        new Operator[] { Operator.Substraction, Operator.Addition });
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "12-2-5+1-2=4");
  }

  @Test
  public void testWithoutAddition() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0],
        new Operator[] { Operator.Substraction, Operator.Multiplication,
            Operator.Division });
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    Assert.assertEquals(solution.print(), "12-2/5*1*2=4");
  }

  @Test
  public void testWithoutSubstraction() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0],
        new Operator[] { Operator.Addition, Operator.Multiplication,
            Operator.Division });
    MathQuiz solution = mq.solve();
    Assert.assertFalse(solution.isSatisfied());
  }
}
