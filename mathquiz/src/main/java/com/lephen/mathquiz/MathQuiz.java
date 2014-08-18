package com.lephen.mathquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.IntBinaryOperator;

class Operator {
  final char op;
  final IntBinaryOperator func;

  Operator(char op, IntBinaryOperator func) {
    this.op = op;
    this.func = func;
  }

  public MathQuiz apply(MathQuiz mathQuiz) {
    int newacc;
    try {
      newacc = func.applyAsInt(mathQuiz.acc, mathQuiz.nos[mathQuiz.ops.length]);
    } catch (UnsupportedOperationException ex) {
      return MathQuiz.NO_RESULT;
    }
    return mathQuiz.step(newacc, this);
  }

  private static final class Addition extends Operator {
    Addition() {
      super('+', (a, b) -> a + b);
    }
  }

  private static final class Substraction extends Operator {
    Substraction() {
      super('-', (a, b) -> a - b);
    }
  }

  private static final class Multiplication extends Operator {
    Multiplication() {
      super('*', (a, b) -> a * b);
    }
  }

  private static final class Division extends Operator {
    Division() {
      super('/', (a, b) -> {
        if (a % b != 0)
          throw new UnsupportedOperationException(
              "Division results in non-zero remainder");
        if (b == 0)
          throw new UnsupportedOperationException("Division by zero");
        return a / b;
      });
    }
  }

  public static final Operator ADDITION = new Addition();
  public static final Operator SUBSTRACTION = new Substraction();
  public static final Operator MULTIPLICATION = new Multiplication();
  public static final Operator DIVISION = new Division();
  public static final Operator[] VALUES = new Operator[] { ADDITION,
      SUBSTRACTION, MULTIPLICATION, DIVISION };

  public static Operator[] values() {
    return VALUES;
  }
}

public class MathQuiz {

  public static final MathQuiz NO_RESULT = new MathQuiz(new int[0], 0, -1,
      new char[0]);

  public final int[] nos;
  public final int initial;
  public final int result;
  public final int acc;
  public final char[] ops;
  public final Operator[] precedence;

  MathQuiz(int[] nos, int initial, int result, char[] ops) {
    this(nos, initial, result, ops, Operator.values());
  }

  MathQuiz(int[] nos, int initial, int result, char[] ops, Operator[] precedence) {
    this(nos, initial, initial, result, ops, precedence);
  }

  private MathQuiz(int[] nos, int initial, int acc, int result, char[] ops,
      Operator[] precedence) {
    this.nos = nos;
    this.result = result;
    this.initial = initial;
    this.acc = acc;
    this.ops = ops;
    this.precedence = precedence;
  }

  public MathQuiz step(int acc, Operator operator) {
    char[] newops = new char[ops.length + 1];
    System.arraycopy(ops, 0, newops, 0, ops.length);
    newops[newops.length - 1] = operator.op;
    return new MathQuiz(nos, this.initial, acc, result, newops, precedence);
  }

  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int lines = Integer.parseInt(br.readLine());
      for (int i = 0; i < lines; i++) {
        String[] line = br.readLine().split("\\s");
        int count = Integer.parseInt(line[0]);
        int initial = Integer.parseInt(line[1]);
        int result = Integer.parseInt(line[line.length - 1]);
        int[] nos = new int[count - 1];
        for (int j = 0; j < count - 1; j++) {
          nos[j] = Integer.parseInt(line[j + 2]);
        }
        MathQuiz mq = new MathQuiz(nos, initial, result, new char[0]);
        System.out.println(mq.solve());
      }
    } catch (NumberFormatException | IOException e) {
      System.out.println("Unable to read from console, exiting");
      throw new RuntimeException(e);
    }
  }

  public String print() {
    if (!isSatisfied()) {
      return "NO EXPRESSION";
    }

    StringBuilder sb = new StringBuilder().append(initial);
    for (int i = 0; i < nos.length; i++) {
      sb.append(ops[i]).append(nos[i]);
    }
    sb.append('=').append(result);
    return sb.toString();
  }

  boolean isSatisfied() {
    if (nos.length == ops.length && result == acc) {
      return true;
    }

    return false;
  }

  MathQuiz solve() {
    if (isSatisfied()) {
      return this;
    }

    // ran out of nos.
    if (nos.length == ops.length) {
      return NO_RESULT;
    }

    for (Operator op : precedence) {
      MathQuiz next = op.apply(this).solve();
      if (next.isSatisfied()) {
        return next;
      }
    }
    return NO_RESULT;
  }
}
