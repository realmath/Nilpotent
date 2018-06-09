package xyz.realmath.nilpotent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xyz.realmath.MultiVarPolynomial;
import xyz.realmath.Polynomials;
import xyz.realmath.Rational;
import xyz.realmath.Tuple;

@RunWith(JUnit4.class)
public class ZassenhausTest {
  private static final NilpotentLieAlg LIE_ALG =
      new NilpotentLieAlg(new MalcevStructureConstants.Builder(7).build());
  private static int DIM = LIE_ALG.dim();
  private static Tuple<MultiVarPolynomial> X = Polynomials.INSTANCE.basePols(DIM);
  private static Tuple<MultiVarPolynomial> Y = Polynomials.INSTANCE.basePols2(DIM);
  private static final Tuple<MultiVarPolynomial> C2 =
      lieBracket(X, Y).multiply(Rational.Companion.valueOf(1).divide(2));
  private static final Tuple<MultiVarPolynomial> C3 =
      (lieBracket(Y, lieBracket(X, Y)).multiply(Rational.Companion.valueOf(1).divide(3)))
          .add(lieBracket(X, lieBracket(X, Y)).multiply(Rational.Companion.valueOf(1).divide(6)));
  private static final Tuple<MultiVarPolynomial> C4 =
      (lieBracket(Y, lieBracket(Y, lieBracket(X, Y)))
              .add(lieBracket(Y, lieBracket(X, lieBracket(X, Y))))
              .multiply(Rational.Companion.valueOf(1).divide(8)))
          .add(
              lieBracket(X, lieBracket(X, lieBracket(X, Y)))
                  .multiply(Rational.Companion.valueOf(1).divide(24)));
  private static final Tuple<MultiVarPolynomial> C5 =
      // ([[[[Y, X], Y], Y], Y] + [[[[Y, X], X], X], Y]) / 30
      (lieBracket(lieBracket(lieBracket(lieBracket(Y, X), Y), Y), Y)
              .add(lieBracket(lieBracket(lieBracket(lieBracket(Y, X), X), X), Y))
              .multiply(Rational.Companion.valueOf(1).divide(30)))
          // +
          .add(
              // ([[[[Y, X], X], Y], Y] +  [[[Y, X], X], [Y, X]]) / 20
              lieBracket(lieBracket(lieBracket(lieBracket(Y, X), X), Y), Y)
                  .add(lieBracket(lieBracket(lieBracket(Y, X), X), lieBracket(Y, X)))
                  .multiply(Rational.Companion.valueOf(1).divide(20)))
          // + [[[Y, X], Y], [Y, X]] / 10
          .add(
              lieBracket(lieBracket(lieBracket(Y, X), Y), lieBracket(Y, X))
                  .multiply(Rational.Companion.valueOf(1).divide(10)))
          // + [[[[Y, X], X], X], X] / 120
          .add(
              lieBracket(lieBracket(lieBracket(lieBracket(Y, X), X), X), X)
                  .multiply(Rational.Companion.valueOf(1).divide(120)));
  private static final Tuple<MultiVarPolynomial> C6 =
      // [[[[[X, Y], Y], Y], Y], Y] + [[[[[X, Y], X], X], X], Y] / 144
      (lieBracket(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), Y), Y), Y), Y)
              .add(lieBracket(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), X), X), X), Y))
              .multiply(Rational.Companion.valueOf(1).divide(144)))
          // + ([[[[[X, Y], X], Y], Y], Y] + [[[[[X, Y], X], X], Y], Y]
          //    + [[[[X, Y], X], X], [Y, X]]) / 72
          .add(
              lieBracket(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), X), X), Y), Y)
                  .add(lieBracket(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), X), X), Y), Y))
                  .add(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), X), X), lieBracket(Y, X)))
                  .multiply(Rational.Companion.valueOf(1).divide(72)))
          // + ([[[[X, Y], Y], Y], [Y, X]] + [[[[X, Y], X], Y], [Y, X]]) / 24
          .add(
              lieBracket(lieBracket(lieBracket(lieBracket(X, Y), Y), Y), lieBracket(Y, X))
                  .add(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), X), Y), lieBracket(Y, X)))
                  .multiply(Rational.Companion.valueOf(1).divide(24)))
          // [[[[[X, Y], X], X], X], X] / 720
          .add(
              lieBracket(lieBracket(lieBracket(lieBracket(lieBracket(X, Y), X), X), X), X)
                  .multiply(Rational.Companion.valueOf(1).divide(720)));

  private static Tuple<MultiVarPolynomial> lieBracket(
      Tuple<MultiVarPolynomial> left, Tuple<MultiVarPolynomial> right) {
    return LIE_ALG.lieBracket(left, right);
  }

  @Test
  public void orderOneToSix() {}

  @Test
  public void orderSixToOne() {}
}
