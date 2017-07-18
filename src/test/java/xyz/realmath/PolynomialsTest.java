package xyz.realmath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xyz.realmath.MultiVarPolynomial.PolBuilder;
import xyz.realmath.MultiVarPolynomial.TermBuilder;

@RunWith(JUnit4.class)
public class PolynomialsTest {
  @Test
  public void baseVector() {
    Tuple<MultiVarPolynomial> pols = Polynomials.basePols(0);
    assertEquals(0, pols.dim());

    pols = Polynomials.basePols(3);
    assertEquals(
        new PolBuilder().add(new TermBuilder().pow(1, 1).build(), Rational.ONE).build(),
        pols.get(1));
  }

  @Test
  public void baseVector2() {
    Tuple<MultiVarPolynomial> pols = Polynomials.basePols(0);
    assertEquals(0, pols.dim());

    pols = Polynomials.basePols(4);
    assertEquals(
        new PolBuilder().add(new TermBuilder().pow(3, 1).build(), Rational.ONE).build(),
        pols.get(3));
  }
}
