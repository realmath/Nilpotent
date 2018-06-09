package xyz.realmath.nilpotent;

import xyz.realmath.Algebra;
import xyz.realmath.Rational;
import xyz.realmath.Tuple;

public class NilpotentLieGroup {
  private final NilpotentLieAlg lieAlg;
  private final int dim;
  private final Zassenhaus zassenhaus;

  private NilpotentLieGroup(NilpotentLieAlg lieAlg) {
    this.lieAlg = lieAlg;
    dim = lieAlg.dim();
    zassenhaus = new Zassenhaus(lieAlg);
  }

  public static NilpotentLieGroup exp(NilpotentLieAlg lieAlg) {
    return null;
  }

  private int dim() {
    return dim;
  }

  // In strong Malcev coordinate
  // (x_0, ... , x_{n-1}) = exp x_{n-1} X_{n-1} ... exp x_0 X_0

  /** Multiplication in strong Malcev coordinate. Baker-Campbell-Hausdorff. */
  public <T extends Algebra<Rational, T>> Tuple<T> multiplyInMalcev(Tuple<T> x, Tuple<T> y) {
    return null;
  }

  /** Multiplication in exponential coordinate. */
  public <T extends Algebra<Rational, T>> Tuple<T> multiplyInExp(Tuple<T> x, Tuple<T> y) {
    return null;
  }

  /** Inverse in strong Malcev coordinate. */
  public <T extends Algebra<Rational, T>> Tuple<T> inverse(Tuple<T> tuple) {
    return null;
  }

  /** Exponential map in strong Malcev coordinate. */
  public <T extends Algebra<Rational, T>> Tuple<T> exp(Tuple<T> lieAlgVector) {
    for (int i = 0; i < dim; i++) {
      // rationals[i] = exp[i].evaluate(lieAlgVector);
    }
    return null;
  }

  /** Inverse of exponential map in strong Malcev coordinate. */
  public <T extends Algebra<Rational, T>> Tuple<T> log(Tuple<Rational> lieGroupElem) {
    for (int i = 0; i < dim(); i++) {
      // rationals[i] = log[i].evaluate(lieGroupElem);
    }
    return null;
  }
}
