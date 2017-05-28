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
  public void substract() {
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
}
