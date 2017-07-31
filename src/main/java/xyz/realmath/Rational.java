package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.errorprone.annotations.Immutable;
import java.math.BigInteger;

@Immutable
public final class Rational implements Field<Rational> {
  public static final Rational ZERO = Rational.valueOf(0);
  public static final Rational ONE = Rational.valueOf(1);

  private final BigInteger numer;
  private final BigInteger denom;

  private Rational(BigInteger numer, BigInteger denom) {
    checkArgument(!denom.equals(BigInteger.ZERO), "denominator must not be zero");
    BigInteger oldNumer = numer;

    numer = numer / oldNumer.gcd(denom);
    denom = denom / oldNumer.gcd(denom);

    if (denom.signum() == -1) {
      numer = -numer;
      denom = -denom;
    }

    this.numer = numer;
    this.denom = denom;
  }

  private Rational(int i) {
    this(BigInteger.valueOf(i), BigInteger.ONE);
  }

  public static Rational valueOf(int i) {
    return new Rational(i);
  }

  public static Rational factorial(int n) {
    Rational retVal = Rational.ONE;
    for (int i = 1; i <= n; i++) {
      retVal = retVal * i;
    }
    return retVal;
  }

  @Override
  public String toString() {
    if (denom.equals(BigInteger.ONE)) {
      return numer.toString();
    } else {
      return numer.toString() + ":" + denom.toString();
    }
  }

  @Override
  public Rational add(Rational by) {
    BigInteger newDenom = denom * by.denom;
    BigInteger newNumer = numer * by.denom;

    newNumer = newNumer + by.numer * denom;

    return new Rational(newNumer, newDenom);
  }

  @Override
  public Rational add(int i) {
    return this + Rational.valueOf(i);
  }

  @Override
  public Rational multiply(Rational by) {
    return new Rational(numer * by.numer, denom * by.denom);
  }

  @Override
  public Rational multiply(int i) {
    return this * Rational.valueOf(i);
  }

  @Override
  public Rational divide(int i) {
    return this / Rational.valueOf(i);
  }

  @Override
  public Rational negate() {
    return new Rational(-numer, denom);
  }

  @Override
  public Rational reciprocal() {
    return new Rational(denom, numer);
  }

  /** Power. */
  public Rational pow(int n) {
    if (n <= 0) {
      checkArgument(!this.equals(Rational.ZERO), "undefined");
    }
    if (n < 0) {
      return pow(-n).reciprocal();
    }
    Rational retVal = Rational.ONE;
    for (int i = 0; i < n; i++) {
      retVal = retVal * this;
    }
    return retVal;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Rational rational = (Rational) obj;
    return java.util.Objects.equals(numer, rational.numer)
        && java.util.Objects.equals(denom, rational.denom);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(numer, denom);
  }
}
