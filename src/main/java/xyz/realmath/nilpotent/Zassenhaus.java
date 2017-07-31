package xyz.realmath.nilpotent;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import xyz.realmath.MultiVarPolynomial;
import xyz.realmath.Pair;
import xyz.realmath.Polynomials;
import xyz.realmath.Rational;
import xyz.realmath.Tuple;

class Zassenhaus {
  private final NilpotentLieAlg lieAlg;
  private final int dim;
  private final HashMap<Integer, Tuple<MultiVarPolynomial>> zassenhause = new HashMap<>();
  private final HashMap<Pair, Tuple<MultiVarPolynomial>> ff = new HashMap<>();
  HashMap<Tuple<MultiVarPolynomial>, ArrayList<Tuple<MultiVarPolynomial>>> adjointPows =
      new HashMap<>();

  Zassenhaus(NilpotentLieAlg lieAlg) {
    this.lieAlg = lieAlg;
    dim = lieAlg.dim();
  }

  Tuple<MultiVarPolynomial> get(int n) {
    checkArgument(n >= 2, "n < 2");
    checkArgument(n < dim, "n >= dim");
    if (zassenhause.containsKey(n)) {
      return zassenhause.get(n);
    }
    Tuple<MultiVarPolynomial> retVal;
    if (n <= 4) {
      retVal = ff(1, n - 1).multiply(Rational.valueOf(n).reciprocal());
    } else {
      retVal = ff((n - 1) / 2, n - 1).multiply(Rational.valueOf(n).reciprocal());
    }
    zassenhause.put(n, retVal);
    return retVal;
  }

  private Tuple<MultiVarPolynomial> ff(int n, int k) {
    checkArgument(n >= 1, "n < 1");
    checkArgument(k >= n, "k < n");
    Pair pair = new Pair(n, k);
    if (ff.containsKey(new Pair(n, k))) {
      return ff.get(pair);
    }
    Tuple<MultiVarPolynomial> retVal;
    if (n == 1) {
      retVal = f1k(k);
    } else {
      retVal = fnk(n, k);
    }
    ff.put(pair, retVal);
    return retVal;
  }

  private Tuple<MultiVarPolynomial> f1k(int k) {
    Tuple<MultiVarPolynomial> retVal = Polynomials.zeroTuple(dim);
    for (int j = 1; j <= k; j++) {
      Rational coef = Rational.factorial(j).multiply(Rational.factorial(k - j)).reciprocal();
      if (k % 2 != 0) {
        coef = coef.negate();
      }
      retVal =
          retVal.add(adjointYPow(adjointXPow(Polynomials.basePols2(dim), j), k - j).multiply(coef));
    }
    return retVal;
  }

  private Tuple<MultiVarPolynomial> fnk(int n, int k) {
    Tuple<MultiVarPolynomial> retVal = Polynomials.zeroTuple(dim);
    for (int j = 0; j <= k / n - 1; j++) {
      Rational coef = Rational.factorial(j).reciprocal();
      if (j % 2 != 0) {
        coef = coef.negate();
      }
      retVal = retVal.add(adjointPow(get(n), ff(n - 1, k - n * j), j).multiply(coef));
    }
    return retVal;
  }

  private Tuple<MultiVarPolynomial> adjointXPow(Tuple<MultiVarPolynomial> v, int k) {
    return adjointPow(Polynomials.basePols(dim), v, k);
  }

  private Tuple<MultiVarPolynomial> adjointYPow(Tuple<MultiVarPolynomial> v, int k) {
    return adjointPow(Polynomials.basePols2(dim), v, k);
  }

  private Tuple<MultiVarPolynomial> adjointPow(
      Tuple<MultiVarPolynomial> left, Tuple<MultiVarPolynomial> right, int k) {
    checkArgument(k >= 0, "k < 0");
    if (adjointPows.containsKey(left) && adjointPows.get(left).size() > k) {
      return adjointPows.get(left).get(k);
    }
    if (k == 0) {
      ArrayList<Tuple<MultiVarPolynomial>> list = new ArrayList<>();
      list.add(right);
      adjointPows.put(left, list);
      return right;
    }
    Tuple<MultiVarPolynomial> retVal = lieAlg.lieBracket(left, adjointPow(left, right, k - 1));
    adjointPows.get(left).add(retVal);
    return retVal;
  }
}
