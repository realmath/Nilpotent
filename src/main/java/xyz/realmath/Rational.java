package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.errorprone.annotations.Immutable;
import java.math.BigInteger;
import xyz.realmath.nilpotent.Field;

@Immutable
public final class Rational implements Algebra<Rational, Rational>, Field<Rational> {
  public static final Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);
  public static final Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);

  private final BigInteger numer;
  private final BigInteger denom;

  public Rational(BigInteger numer, BigInteger denom) {
    checkArgument(!denom.equals(BigInteger.ZERO), "denominator must not be zero");
    BigInteger oldNumer = numer;

    numer = numer.divide(oldNumer.gcd(denom));
    denom = denom.divide(oldNumer.gcd(denom));

    if (denom.signum() == -1) {
      numer = numer.negate();
      denom = denom.negate();
    }

    this.numer = numer;
    this.denom = denom;
  }

  public Rational(int i) {
    numer = BigInteger.valueOf(i);
    denom = BigInteger.ONE;
  }

  public Rational(int n, int d) {
    this(BigInteger.valueOf(n), BigInteger.valueOf(d));
  }

  public static Rational factorial(int n) {
    Rational retVal = Rational.ONE;
    for (int i = 1; i <= n; i++) {
      retVal = retVal.multiply(new Rational(i));
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
    BigInteger newDenom = denom.multiply(by.denom);
    BigInteger newNumer = numer.multiply(by.denom);

    newNumer = newNumer.add(by.numer.multiply(denom));

    return new Rational(newNumer, newDenom);
  }

  @Override
  public Rational multiply(Rational by) {
    return new Rational(numer.multiply(by.numer), denom.multiply(by.denom));
  }

  @Override
  public Rational negate() {
    return new Rational(numer.negate(), denom);
  }

  @Override
  public Rational reciprocal() {
    return new Rational(denom, numer);
  }

  public Rational pow(int n) {
    if (n <= 0) {
      checkArgument(!this.equals(Rational.ZERO), "undefined");
    }
    if (n < 0) {
      return pow(-n).reciprocal();
    }
    Rational retVal = Rational.ONE;
    for (int i = 0; i < n; i++) {
      retVal = retVal.multiply(this);
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
