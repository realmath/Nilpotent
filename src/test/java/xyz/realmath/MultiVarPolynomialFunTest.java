package xyz.realmath;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MultiVarPolynomialFunTest {
  @Test
  public void add() {
    MultiVarPolynomialFun pol1 =
        new MultiVarPolynomialFun.Builder(2)
            .addMonoid(Arrays.asList(2, 3), Rational.ONE)
            .addMonoid(Arrays.asList(3, 2), new Rational(2))
            .addMonoid(Arrays.asList(1, 3), new Rational(3))
            .build();
    MultiVarPolynomialFun pol2 =
        new MultiVarPolynomialFun.Builder(2)
            .addMonoid(Arrays.asList(0, 3), Rational.ONE)
            .addMonoid(Arrays.asList(3, 2), new Rational(5))
            .addMonoid(Arrays.asList(1, 3), new Rational(-3))
            .build();
    MultiVarPolynomialFun pol3 =
        new MultiVarPolynomialFun.Builder(2)
            .addMonoid(Arrays.asList(0, 3), Rational.ONE)
            .addMonoid(Arrays.asList(2, 3), Rational.ONE)
            .addMonoid(Arrays.asList(3, 2), new Rational(7))
            .build();

    assertEquals(pol3, pol1.add(pol2));
    assertEquals(pol3.hashCode(), pol1.add(pol2).hashCode());
  }

  @Test
  public void evaluate() {
    MultiVarPolynomialFun pol =
        new MultiVarPolynomialFun.Builder(2)
            .addMonoid(Arrays.asList(2, 3), Rational.ONE)
            .addMonoid(Arrays.asList(3, 2), new Rational(2))
            .addMonoid(Arrays.asList(1, 3), new Rational(3))
            .build();

    assertEquals(
        new Rational(2 * 2 * 3 * 3 * 3 + 2 * (2 * 2 * 2 * 3 * 3) + 3 * (2 * 3 * 3 * 3)),
        pol.evaluate(new Tuple(new Rational(2), new Rational(3))));
  }
}
