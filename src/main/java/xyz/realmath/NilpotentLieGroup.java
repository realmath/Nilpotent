package xyz.realmath;

public class NilpotentLieGroup {
  private final NilpotentLieAlg lieAlg;
  private final MultiVarPolynomialFun[] exp;
  private final MultiVarPolynomialFun[] log;

  private NilpotentLieGroup(NilpotentLieAlg lieAlg) {
    this.lieAlg = lieAlg;
    exp = new MultiVarPolynomialFun[dim()];
    log = new MultiVarPolynomialFun[dim()];
    initExp();
    initLog();
  }

  public static NilpotentLieGroup exp(NilpotentLieAlg lieAlg) {
    return null;
  }

  private void initExp() {}

  private void initLog() {}

  public int dim() {
    return lieAlg.dim();
  }

  // In strong Malcev coordinate
  // (x0, ... , x(n-1)) = exp x(n-1)X(n-1) ... exp x0 X0

  /** Multiplication in strong Malcev coordinate. Baker-Campbell-Hausdorff. */
  public Tuple multiplyInMalcev(Tuple left, Tuple right) {
    return null;
  }

  /** Multiplication in exponential coordinate. */
  public Tuple multiplyInExp(Tuple left, Tuple right) {
    return null;
  }

  /** Inverse in strong Malcev coordinate. */
  public Tuple inverse(Tuple tuple) {
    return null;
  }

  /** Exponential map in strong Malcev coordinate. */
  public Tuple exp(Tuple lieAlgVector) {
    Rational[] rationals = new Rational[dim()];
    for (int i = 0; i < dim(); i++) {
      rationals[i] = exp[i].evaluate(lieAlgVector);
    }
    return new Tuple(rationals);
  }

  /** Inverse of exponential map in strong Malcev coordinate. */
  public Tuple log(Tuple lieGroupElem) {
    Rational[] rationals = new Rational[dim()];
    for (int i = 0; i < dim(); i++) {
      rationals[i] = log[i].evaluate(lieGroupElem);
    }
    return new Tuple(rationals);
  }
}
