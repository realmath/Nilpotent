package xyz.realmath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TupleTest {
  @Test
  public void dimZero() {
    Tuple t = new Tuple();
    assertEquals(0, t.length);
  }

  @Test
  public void dimOne() {
    Tuple t = new Tuple(Rational.ONE);
    assertEquals(1, t.length);
  }

  @Test(expected = NullPointerException.class)
  public void nullArgs() {
    Rational[] rationals = null;
    new Tuple(rationals);
  }

  @Test(expected = NullPointerException.class)
  public void nullArg() {
    new Tuple(Rational.ONE, null);
  }

  @Test
  public void iterator() {
    Tuple t = new Tuple(Rational.ONE, new Rational(2), new Rational(3));
    int i = 1;
    for (Rational r : t) {
      assertEquals(new Rational(i), r);
      i++;
    }
  }

  @Test
  public void get() {
    Tuple t1 = new Tuple(Rational.ONE, Rational.ZERO, Rational.ONE);
    assertEquals(Rational.ONE, t1.get(2));
  }

  @Test
  public void add() {
    Tuple t = new Tuple();
    assertEquals(t, t.add(t));

    Tuple t1 = new Tuple(Rational.ONE, Rational.ZERO, Rational.ONE);
    Tuple t2 = new Tuple(Rational.ZERO, Rational.ONE, Rational.ONE);
    Tuple t3 = new Tuple(Rational.ONE, Rational.ONE, new Rational(2));

    assertEquals(t3, t1.add(t2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addWrongDim() {
    Tuple t1 = new Tuple(Rational.ONE);
    Tuple t2 = new Tuple(Rational.ZERO, Rational.ZERO);
    t1.add(t2);
  }

  @Test
  public void minus() {
    Tuple t = new Tuple();
    assertEquals(t, t.minus(t));

    Tuple t1 = new Tuple(Rational.ONE, Rational.ZERO, Rational.ONE);
    Tuple t2 = new Tuple(Rational.ZERO, Rational.ONE, Rational.ONE);
    Tuple t3 = new Tuple(Rational.ONE, Rational.ONE.negate(), Rational.ZERO);

    assertEquals(t3, t1.minus(t2));
  }

  @Test
  public void multiply() {
    Tuple t = new Tuple();
    assertEquals(t, t.multiply(new Rational(3)));

    Tuple t1 = new Tuple(Rational.ONE, Rational.ZERO, Rational.ONE);
    Tuple t3 = new Tuple(new Rational(3), Rational.ZERO, new Rational(3));

    assertEquals(t3, t1.multiply(new Rational(3)));
  }

  @Test
  public void hashCodeTest() {
    Tuple t1 = new Tuple(Rational.ONE, Rational.ZERO, Rational.ONE);
    Tuple t2 = new Tuple(Rational.ONE, Rational.ZERO, Rational.ONE);

    assertEquals(t1.hashCode(), t2.hashCode());
  }
}
