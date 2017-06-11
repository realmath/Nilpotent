package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.errorprone.annotations.Immutable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Immutable
public class Tuple implements Iterable<Rational> {

  public final int length;

  @SuppressWarnings("Immutable") // only written in constructor
  final Rational[] rationals;

  public Tuple(Rational... rationals) {
    this.rationals = new Rational[rationals.length];
    this.length = rationals.length;

    int i = 0;
    for (Rational rational : rationals) {
      checkNotNull(rational, "rationals");
      this.rationals[i] = rationals[i];
      i++;
    }
  }

  public Rational get(int idx) {
    return rationals[idx];
  }

  public Tuple add(Tuple tuple) {
    checkArgument(tuple.length == length, "tuple.length != length");

    Rational[] sums = new Rational[length];
    for (int i = 0; i < length; i++) {
      sums[i] = this.rationals[i].add(tuple.rationals[i]);
    }

    return new Tuple(sums);
  }

  public Tuple minus(Tuple tuple) {
    checkArgument(tuple.length == length, "tuple.length != length");

    Rational[] diffs = new Rational[length];
    for (int i = 0; i < length; i++) {
      diffs[i] = this.rationals[i].minus(tuple.rationals[i]);
    }

    return new Tuple(diffs);
  }

  public Tuple multiply(Rational rational) {

    Rational[] prods = new Rational[length];
    for (int i = 0; i < length; i++) {
      prods[i] = this.rationals[i].multiply(rational);
    }

    return new Tuple(prods);
  }

  @Override
  public Iterator<Rational> iterator() {
    return new Iterator<Rational>() {
      int i;

      @Override
      public boolean hasNext() {
        return i < length;
      }

      @Override
      public Rational next() {
        i++;
        if (i > rationals.length) {
          throw new NoSuchElementException();
        }
        return rationals[i - 1];
      }
    };
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Tuple rationals1 = (Tuple) obj;
    return Arrays.equals(rationals, rationals1.rationals);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(rationals);
  }
}
