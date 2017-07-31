package xyz.realmath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xyz.realmath.MultiVarPolynomial.PolBuilder;
import xyz.realmath.MultiVarPolynomial.TermBuilder;

@RunWith(JUnit4.class)
public class MultiVarPolynomialTest {
  @Test
  public void add() {
    MultiVarPolynomial pol1 =
        new PolBuilder()
            .add(new TermBuilder().pow(0, 2).pow(1, 3).build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 3).pow(1, 2).build(), Rational.valueOf(2))
            .add(new TermBuilder().pow(0, 1).pow(1, 3).build(), Rational.valueOf(3))
            .add(new TermBuilder().pow(4, 1).build(), Rational.ONE)
            .add(new TermBuilder().pow(6, 1).build(), Rational.ONE)
            .build();
    MultiVarPolynomial pol2 =
        new PolBuilder()
            .add(new TermBuilder().pow(1, 3).build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 3).pow(1, 2).build(), Rational.valueOf(5))
            .add(new TermBuilder().pow(0, 1).pow(1, 3).build(), Rational.valueOf(-3))
            .add(new TermBuilder().pow(5, 1).build(), Rational.ONE)
            .add(new TermBuilder().pow(6, 1).build(), Rational.ONE.negate())
            .build();
    MultiVarPolynomial pol3 =
        new PolBuilder()
            .add(new TermBuilder().pow(1, 3).build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 2).pow(1, 3).build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 3).pow(1, 2).build(), Rational.valueOf(7))
            .add(new TermBuilder().pow(4, 1).build(), Rational.ONE)
            .add(new TermBuilder().pow(5, 1).build(), Rational.ONE)
            .build();

    assertEquals(pol3, pol1.add(pol2));
    assertEquals(pol3.hashCode(), pol1.add(pol2).hashCode());
  }

  @Test
  public void pow() {
    MultiVarPolynomial pol =
        new PolBuilder()
            .add(new TermBuilder().build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 1).build(), Rational.ONE)
            .build();
    MultiVarPolynomial expected =
        new PolBuilder()
            .add(new TermBuilder().build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 1).build(), Rational.valueOf(2))
            .add(new TermBuilder().pow(0, 2).build(), Rational.ONE)
            .build();
    assertEquals(expected, pol.pow(2));
  }

  @Test
  public void multiply() {
    MultiVarPolynomial left =
        new PolBuilder()
            .add(new TermBuilder().build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 1).build(), Rational.ONE)
            .build();
    MultiVarPolynomial right =
        new PolBuilder()
            .add(new TermBuilder().build(), Rational.ONE)
            .add(new TermBuilder().pow(1, 1).build(), Rational.ONE)
            .build();
    MultiVarPolynomial expected =
        new PolBuilder()
            .add(new TermBuilder().build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 1).build(), Rational.ONE)
            .add(new TermBuilder().pow(1, 1).build(), Rational.ONE)
            .add(new TermBuilder().pow(0, 1).pow(1, 1).build(), Rational.ONE)
            .build();
    assertEquals(expected, left.multiply(right));
  }
}
