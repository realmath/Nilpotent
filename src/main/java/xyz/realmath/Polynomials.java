package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import xyz.realmath.MultiVarPolynomial.PolBuilder;
import xyz.realmath.MultiVarPolynomial.TermBuilder;

public class Polynomials {
  /** [X_0, X_1, ..., X_{n-1}] */
  public static Tuple<MultiVarPolynomial> basePols(int dim) {
    checkArgument(dim >= 0, "dim < 0");

    MultiVarPolynomial[] retVal = new MultiVarPolynomial[dim];
    for (int i = 0; i < dim; i++) {
      retVal[i] = new PolBuilder().add(new TermBuilder().pow(i, 1).build(), Rational.ONE).build();
    }
    return new ArrayTuple<>(retVal);
  }

  /** [X_n, X_{n+1}, ..., X_{2n-1}] */
  public static Tuple<MultiVarPolynomial> basePols2(int dim) {
    checkArgument(dim >= 0, "dim < 0");

    MultiVarPolynomial[] retVal = new MultiVarPolynomial[dim];
    for (int i = 0; i < dim; i++) {
      retVal[i] =
          new PolBuilder().add(new TermBuilder().pow(i + dim, 1).build(), Rational.ONE).build();
    }
    return new ArrayTuple<>(retVal);
  }

  public static Tuple<MultiVarPolynomial> zeroTuple(int dim) {
    ArrayList<MultiVarPolynomial> list = new ArrayList<>();
    for (int i = 0; i < dim; i++) {
      list.add(MultiVarPolynomial.ZERO);
    }
    return new ArrayTuple<>(list);
  }
}
