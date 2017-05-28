package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.errorprone.annotations.Immutable;
import java.math.BigInteger;

@Immutable
public class Rational {
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

  @Override
  public String toString() {
    if (denom.equals(BigInteger.ONE)) {
      return numer.toString();
    } else {
      return numer.toString() + ":" + denom.toString();
    }
  }

  public Rational add(Rational by) {
    BigInteger newDenom = denom.multiply(by.denom);
    BigInteger newNumer = numer.multiply(by.denom);

    newNumer = newNumer.add(by.numer.multiply(denom));

    return new Rational(newNumer, newDenom);
  }

  public Rational multiply(Rational by) {
    return new Rational(numer.multiply(by.numer), denom.multiply(by.denom));
  }

  public Rational negate() {
    Rational retVal = new Rational(numer.negate(), denom);
    return retVal;
  }

  public Rational reciprocal() {
    return new Rational(denom, numer);
  }

  public Rational minus(Rational other) {
    return this.add(other.negate());
  }

  public Rational dividedBy(Rational other) {
    return this.multiply(other.reciprocal());
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
