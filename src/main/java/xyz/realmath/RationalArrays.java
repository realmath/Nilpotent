package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;

public final class RationalArrays {
  public static Rational[] zeroTuple(int length) {
    checkArgument(length >= 0, "length < 0");

    Rational[] rationals = new Rational[length];
    for (int i = 0; i < length; i++) {
      rationals[i] = Rational.ZERO;
    }
    return rationals;
  }

  public static Rational[] base(int length, int idx) {
    checkArgument(idx >= 0, "idx < 0");
    checkArgument(idx < length, "idx >= length");
    Rational[] rationals = new Rational[length];
    for (int i = 0; i < length; i++) {
      if (i == idx) {
        rationals[i] = Rational.ONE;
      } else {
        rationals[i] = Rational.ZERO;
      }
    }
    return rationals;
  }
}
