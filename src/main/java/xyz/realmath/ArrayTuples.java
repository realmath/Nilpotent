package xyz.realmath;

public final class ArrayTuples {
  public static ArrayTuple<Rational> rationalTuple(int... ints) {
    Rational[] rationals = new Rational[ints.length];
    for (int i = 0; i < ints.length; i++) {
      rationals[i] = ints[i];
    }
    return new ArrayTuple<>(rationals);
  }

  public static ArrayTuple<MultiVarPolynomial> polynomialTuple(int... ints) {
    MultiVarPolynomial[] pols = new MultiVarPolynomial[ints.length];
    for (int i = 0; i < ints.length; i++) {
      pols[i] = ints[i];
    }
    return new ArrayTuple<>(pols);
  }
}
