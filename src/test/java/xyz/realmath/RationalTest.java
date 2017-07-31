package xyz.realmath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RationalTest {
  @Test(expected = IllegalArgumentException.class)
  public void zeroDenum() {
    Rational.valueOf(1).divide(0);
  }

  @Test
  public void equal() {
    Rational rat1 = Rational.valueOf(6) / 8;
    Rational rat2 = Rational.valueOf(9) / 12;

    assertEquals(rat1, rat2);
    assertEquals(rat1.hashCode(), rat2.hashCode());
    assertEquals(Rational.ONE, Rational.valueOf(1));
  }

  @Test
  public void negate() {
    Rational rat1 = Rational.valueOf(6) / (-8);
    Rational rat2 = Rational.valueOf(6) / 8;

    assertEquals(Rational.ZERO, rat1 + rat2);
    assertEquals(Rational.ZERO, rat2 + rat1);
  }

  @Test
  public void multiply() {
    Rational rat1 = Rational.valueOf(6) / 8;
    Rational rat2 = Rational.valueOf(2) / 3;
    Rational rat3 = Rational.ONE / 2;

    assertEquals(rat3, rat1 * rat2);
  }

  @Test
  public void reciprocal() {
    Rational rat1 = Rational.valueOf(6) / 8;
    Rational rat2 = Rational.valueOf(4) / 3;

    assertEquals(rat2, rat1.reciprocal());
  }

  @Test
  public void subtract() {
    Rational rat1 = Rational.valueOf(6) / 8;
    Rational rat2 = Rational.valueOf(4) / 3;
    Rational rat3 = Rational.valueOf(-7) / 12;

    assertEquals(rat3, rat1 - rat2);
  }

  @Test
  public void divideBy() {
    Rational rat1 = Rational.valueOf(6) / 8;
    Rational rat2 = Rational.valueOf(4) / 3;
    Rational rat3 = Rational.valueOf(9) / 16;

    assertEquals(rat3, rat1 / rat2);
  }

  @Test
  public void toStringTest() {
    Rational rat = Rational.valueOf(7) / -12;

    assertEquals("-7:12", rat.toString());
  }

  @Test
  public void pow() {
    Rational r = Rational.valueOf(2) / -3;
    Rational v = Rational.valueOf(-27) / 8;
    assertEquals(v, r.pow(-3));
  }

  @Test
  public void factorial() {
    assertEquals(Rational.ONE, Rational.factorial(0));
    assertEquals(Rational.ONE, Rational.factorial(1));
    assertEquals(Rational.valueOf(2), Rational.factorial(2));
    assertEquals(Rational.valueOf(6), Rational.factorial(3));
    assertEquals(Rational.valueOf(24), Rational.factorial(4));
  }
}
