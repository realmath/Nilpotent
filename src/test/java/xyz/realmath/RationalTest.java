package xyz.realmath;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RationalTest {
  @Test(expected = IllegalArgumentException.class)
  public void zeroDenum() {
    new Rational(BigInteger.ONE, BigInteger.ZERO);
  }

  @Test
  public void equal() {
    Rational rat1 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L));
    Rational rat2 = new Rational(BigInteger.valueOf(9L), BigInteger.valueOf(12L));

    assertEquals(rat1, rat2);
    assertEquals(rat1.hashCode(), rat2.hashCode());
    assertEquals(Rational.ONE, new Rational(1));
  }

  @Test
  public void negate() {
    Rational rat1 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L)).negate();
    Rational rat2 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L));

    assertEquals(Rational.ZERO, rat1.add(rat2));
    assertEquals(Rational.ZERO, rat2.add(rat1));
  }

  @Test
  public void multiply() {
    Rational rat1 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L));
    Rational rat2 = new Rational(BigInteger.valueOf(2L), BigInteger.valueOf(3L));
    Rational rat3 = new Rational(BigInteger.valueOf(1L), BigInteger.valueOf(2L));

    assertEquals(rat3, rat1.multiply(rat2));
  }

  @Test
  public void reciprocal() {
    Rational rat1 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L));
    Rational rat2 = new Rational(BigInteger.valueOf(4L), BigInteger.valueOf(3L));

    assertEquals(rat2, rat1.reciprocal());
  }

  @Test
  public void subtract() {
    Rational rat1 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L));
    Rational rat2 = new Rational(BigInteger.valueOf(4L), BigInteger.valueOf(3L));
    Rational rat3 = new Rational(BigInteger.valueOf(-7L), BigInteger.valueOf(12L));

    assertEquals(rat3, rat1.minus(rat2));
  }

  @Test
  public void divideBy() {
    Rational rat1 = new Rational(BigInteger.valueOf(6L), BigInteger.valueOf(8L));
    Rational rat2 = new Rational(BigInteger.valueOf(4L), BigInteger.valueOf(3L));
    Rational rat3 = new Rational(BigInteger.valueOf(9L), BigInteger.valueOf(16L));

    assertEquals(rat3, rat1.dividedBy(rat2));
  }

  @Test
  public void toStringTest() {
    Rational rat = new Rational(BigInteger.valueOf(7L), BigInteger.valueOf(-12L));

    assertEquals("-7:12", rat.toString());
  }

  @Test
  public void intConstructor() {
    Rational minusTwo = new Rational(BigInteger.ONE.add(BigInteger.ONE).negate(), BigInteger.ONE);

    assertEquals(minusTwo, new Rational(-2));
  }

  @Test
  public void pow() {
    Rational r = new Rational(2).dividedBy(new Rational(-3));
    Rational v = new Rational(-27).dividedBy(new Rational(8));
    assertEquals(v, r.pow(-3));
  }

  @Test
  public void factorial() {
    assertEquals(Rational.ONE, Rational.factorial(0));
    assertEquals(Rational.ONE, Rational.factorial(1));
    assertEquals(new Rational(2), Rational.factorial(2));
    assertEquals(new Rational(6), Rational.factorial(3));
    assertEquals(new Rational(24), Rational.factorial(4));
  }
}
