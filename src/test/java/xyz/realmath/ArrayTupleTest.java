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
    ArrayTuple<Rational> t = ArrayTuples.rationalTuple(1, 2, 3);
    int i = 1;
    for (Rational r : t) {
      assertEquals(Rational.valueOf(i), r);
      i++;
    }
  }

  @Test
  public void get() {
    ArrayTuple<Rational> t1 = ArrayTuples.rationalTuple(1, 0, 1);
    assertEquals(Rational.ONE, t1[2]);
  }

  @Test
  public void add() {
    ArrayTuple<Rational> t = new ArrayTuple<>();
    assertEquals(t, t + t);

    ArrayTuple<Rational> t1 = ArrayTuples.rationalTuple(1, 0, 1);
    ArrayTuple<Rational> t2 = ArrayTuples.rationalTuple(0, 1, 1);
    ArrayTuple<Rational> t3 = ArrayTuples.rationalTuple(1, 1, 2);

    assertEquals(t3, t1 + t2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addWrongDim() {
    ArrayTuple<Rational> t1 = ArrayTuples.rationalTuple(1);
    ArrayTuple<Rational> t2 = ArrayTuples.rationalTuple(0, 0);
    t1.add(t2);
  }

  @Test
  public void minus() {
    Tuple<Rational> t = new ArrayTuple<>();
    assertEquals(t, t - t);

    Tuple<Rational> t1 = ArrayTuples.rationalTuple(1, 0, 1);
    Tuple<Rational> t2 = ArrayTuples.rationalTuple(0, 1, 1);
    Tuple<Rational> t3 = ArrayTuples.rationalTuple(1, -1, 0);

    assertEquals(t3, t1 - t2);
  }

  @Test
  public void multiply() {
    ArrayTuple<Rational> t = new ArrayTuple<>();
    assertEquals(t, t * Rational.valueOf(3));

    ArrayTuple<Rational> t1 = ArrayTuples.rationalTuple(1, 0, 1);
    ArrayTuple<Rational> t3 = ArrayTuples.rationalTuple(3, 0, 3);
    assertEquals(t3, t1 * Rational.valueOf(3));
  }

  @Test
  public void hashCodeTest() {
    ArrayTuple<Rational> t1 = ArrayTuples.rationalTuple(1, 0, 1);
    ArrayTuple<Rational> t2 = ArrayTuples.rationalTuple(1, 0, 1);

    assertEquals(t1.hashCode(), t2.hashCode());
  }
}
