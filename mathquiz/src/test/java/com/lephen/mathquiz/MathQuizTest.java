package com.lephen.mathquiz;

import org.junit.Assert;
import org.junit.Test;

public class MathQuizTest {

  @Test
  public void testExample1() {
    MathQuiz mq = new MathQuiz(new int[] { 7, 4 }, 5, 3, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    System.out.println(solution);
  }

  @Test
  public void testExample2() {
    MathQuiz mq = new MathQuiz(new int[] { 1 }, 1, 2000, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertFalse(solution.isSatisfied());
    System.out.println(solution);
  }

  @Test
  public void testExample3() {
    MathQuiz mq = new MathQuiz(new int[] { 2, 5, 1, 2 }, 12, 4, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    System.out.println(solution);
  }

  @Test
  public void testExample4() {
    MathQuiz mq = new MathQuiz(new int[] { 12, 5, -1, 2 }, 2, 4, new char[0]);
    MathQuiz solution = mq.solve();
    Assert.assertTrue(solution.isSatisfied());
    System.out.println(solution);
  }
}
