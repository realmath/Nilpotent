package xyz.realmath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SubSpaceTest {

  @Test
  public void span() {
    ArrayTuple<Rational> t1 =
        new ArrayTuple<>(new Rational(0), new Rational(1), new Rational(2), new Rational(3));
    ArrayTuple<Rational> t2 =
        new ArrayTuple<>(new Rational(0), new Rational(2), new Rational(4), new Rational(6));
    ArrayTuple<Rational> t3 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(2), new Rational(3));
    ArrayTuple<Rational> t4 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(1), new Rational(3));
    ArrayTuple<Rational> t5 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(1), new Rational(1));
    ArrayTuple<Rational> t6 =
        new ArrayTuple<>(new Rational(7), new Rational(-1), new Rational(3), new Rational(19));

    SubSpace s = SubSpace.span(t1);
    assertEquals(1, s.dim());

    s = SubSpace.span(t1, t2);
    assertEquals(1, s.dim());

    s = SubSpace.span(t1, t3);
    assertEquals(2, s.dim());

    s = SubSpace.span(t1, t3, t4);
    assertEquals(3, s.dim());

    s = SubSpace.span(t1, t3, t4, t5);
    assertEquals(4, s.dim());

    s = SubSpace.span(t1, t3, t4, t5, t6);
    assertEquals(4, s.dim());
  }

  @Test
  public void zeroDim() {
    SubSpace s = SubSpace.zeroDim(3);
    assertEquals(0, s.dim());
  }

  @Test
  public void zeroEmbedding() {
    SubSpace s = SubSpace.zeroDim(0);
    assertTrue(s.contains(new ArrayTuple<>()));
    assertEquals(0, s.dim());
    assertEquals(s.dim(), s.basis().size());
  }

  @Test
  public void contains() {
    ArrayTuple<Rational> t1 =
        new ArrayTuple<>(new Rational(0), new Rational(1), new Rational(2), new Rational(3));
    ArrayTuple<Rational> t2 =
        new ArrayTuple<>(new Rational(0), new Rational(2), new Rational(4), new Rational(6));
    ArrayTuple<Rational> t3 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(2), new Rational(3));
    ArrayTuple<Rational> t4 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(1), new Rational(3));
    ArrayTuple<Rational> t5 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(1), new Rational(1));
    ArrayTuple<Rational> t6 =
        new ArrayTuple<>(new Rational(7), new Rational(-1), new Rational(3), new Rational(19));

    SubSpace s = SubSpace.span(t1);
    assertTrue(s.contains(t2));
    for (Tuple<Rational> t : s.basis()) {
      assertTrue(s.contains(t));
    }

    s = SubSpace.span(t1, t3);
    assertTrue(s.contains(t2));
    assertTrue(s.contains(t3));
    assertFalse(s.contains(t4));
    for (Tuple<Rational> t : s.basis()) {
      assertTrue(s.contains(t));
    }

    s = SubSpace.span(t1, t2, t3, t4);
    assertFalse(s.contains(t5));
    for (Tuple<Rational> t : s.basis()) {
      assertTrue(s.contains(t));
    }

    s = SubSpace.span(t1, t2, t3, t4, t5);
    assertTrue(s.contains(t6));
    for (Tuple<Rational> t : s.basis()) {
      assertTrue(s.contains(t));
    }
  }

  @Test
  public void dim() {
    ArrayTuple<Rational> t1 =
        new ArrayTuple<>(new Rational(0), new Rational(1), new Rational(2), new Rational(3));
    ArrayTuple<Rational> t2 =
        new ArrayTuple<>(new Rational(0), new Rational(2), new Rational(4), new Rational(6));
    ArrayTuple<Rational> t3 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(2), new Rational(3));
    ArrayTuple<Rational> t4 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(1), new Rational(3));
    ArrayTuple<Rational> t5 =
        new ArrayTuple<>(new Rational(1), new Rational(1), new Rational(1), new Rational(1));
    ArrayTuple<Rational> t6 =
        new ArrayTuple<>(new Rational(7), new Rational(-1), new Rational(3), new Rational(19));

    SubSpace s = SubSpace.span(t1);
    assertEquals(1, s.dim());
    assertEquals(s.dim(), s.basis().size());

    s = SubSpace.span(t1, t2);
    assertEquals(1, s.dim());
    assertEquals(s.dim(), s.basis().size());

    s = SubSpace.span(t1, t3);
    assertEquals(2, s.dim());
    assertEquals(s.dim(), s.basis().size());

    s = SubSpace.span(t1, t2, t3, t4);
    assertEquals(3, s.dim());
    assertEquals(s.dim(), s.basis().size());

    s = SubSpace.span(t1, t2, t3, t4, t5);
    assertEquals(4, s.dim());
    assertEquals(s.dim(), s.basis().size());

    s = SubSpace.span(t1, t2, t3, t4, t5, t6);
    assertEquals(4, s.dim());
    assertEquals(s.dim(), s.basis().size());
  }

  @Test
  public void equals() {
    SubSpace s1 = SubSpace.zeroDim(3);
    SubSpace s2 = SubSpace.zeroDim(3);
    SubSpace s3 = SubSpace.zeroDim(5);

    assertEquals(s1, s2);
    assertNotEquals(s1, s3);

    ArrayTuple<Rational> t1 = new ArrayTuple<>(RationalArrays.base(3, 0));
    ArrayTuple<Rational> t2 = new ArrayTuple<>(RationalArrays.base(3, 1));
    ArrayTuple<Rational> t3 = new ArrayTuple<>(RationalArrays.base(3, 2));
    ArrayTuple<Rational> t4 = new ArrayTuple<>(Rational.ONE, Rational.ONE, Rational.ONE);

    SubSpace s4 = SubSpace.span(t1, t2, t3);
    SubSpace s5 = SubSpace.span(t1, t2, t4);
    assertEquals(s4, s5);
    assertEquals(s4.hashCode(), s5.hashCode());

    SubSpace s6 = SubSpace.span(t1, t2);
    SubSpace s7 = SubSpace.span(t1, t4);
    assertNotEquals(s6, s7);
  }

  @Test
  public void hashCodeTest() {
    SubSpace s1 = SubSpace.zeroDim(3);
    SubSpace s2 = SubSpace.zeroDim(3);

    assertEquals(s1.hashCode(), s2.hashCode());
  }
}
