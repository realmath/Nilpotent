package xyz.realmath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ArrayTupleTest {
  @Test
  public void dimZero() {
    ArrayTuple<Rational> t = new ArrayTuple<>();
    assertEquals(0, t.dim());
  }

  @Test
  public void dimOne() {
    ArrayTuple<Rational> t = new ArrayTuple<>(Rational.ONE);
    assertEquals(1, t.dim());
  }

  @Test(expected = NullPointerException.class)
  public void nullArgs() {
    Rational[] rationals = null;
    new ArrayTuple<>(rationals);
  }

  @Test(expected = NullPointerException.class)
  public void nullArg() {
    new ArrayTuple<>(Rational.ONE, null);
  }

  @Test
  public void iterator() {
    ArrayTuple<Rational> t = new ArrayTuple<>(Rational.ONE, new Rational(2), new Rational(3));
    int i = 1;
    for (Rational r : t) {
      assertEquals(new Rational(i), r);
      i++;
    }
  }

  @Test
  public void get() {
    ArrayTuple<Rational> t1 = new ArrayTuple<>(Rational.ONE, Rational.ZERO, Rational.ONE);
    assertEquals(Rational.ONE, t1.get(2));
  }

  @Test
  public void add() {
    ArrayTuple<Rational> t = new ArrayTuple<>();
    assertEquals(t, t.add(t));

    ArrayTuple<Rational> t1 = new ArrayTuple<>(Rational.ONE, Rational.ZERO, Rational.ONE);
    ArrayTuple<Rational> t2 = new ArrayTuple<>(Rational.ZERO, Rational.ONE, Rational.ONE);
    ArrayTuple<Rational> t3 = new ArrayTuple<>(Rational.ONE, Rational.ONE, new Rational(2));

    assertEquals(t3, t1.add(t2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addWrongDim() {
    ArrayTuple<Rational> t1 = new ArrayTuple<>(Rational.ONE);
    ArrayTuple<Rational> t2 = new ArrayTuple<>(Rational.ZERO, Rational.ZERO);
    t1.add(t2);
  }

  @Test
  public void minus() {
    ArrayTuple<Rational> t = new ArrayTuple<>();
    assertEquals(t, t.minus(t));

    ArrayTuple<Rational> t1 = new ArrayTuple<>(Rational.ONE, Rational.ZERO, Rational.ONE);
    ArrayTuple<Rational> t2 = new ArrayTuple<>(Rational.ZERO, Rational.ONE, Rational.ONE);
    ArrayTuple<Rational> t3 = new ArrayTuple<>(Rational.ONE, Rational.ONE.negate(), Rational.ZERO);

    assertEquals(t3, t1.minus(t2));
  }

  @Test
  public void multiply() {
    ArrayTuple<Rational> t = new ArrayTuple<>();
    assertEquals(t, t.multiply(new Rational(3)));

    ArrayTuple<Rational> t1 = new ArrayTuple<>(Rational.ONE, Rational.ZERO, Rational.ONE);
    ArrayTuple<Rational> t3 = new ArrayTuple<>(new Rational(3), Rational.ZERO, new Rational(3));

    assertEquals(t3, t1.multiply((new Rational(3))));
  }

  @Test
  public void hashCodeTest() {
    ArrayTuple<Rational> t1 = new ArrayTuple<>(Rational.ONE, Rational.ZERO, Rational.ONE);
    ArrayTuple<Rational> t2 = new ArrayTuple<>(Rational.ONE, Rational.ZERO, Rational.ONE);

    assertEquals(t1.hashCode(), t2.hashCode());
  }
}
