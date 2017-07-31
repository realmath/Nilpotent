package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class MultiVarPolynomial implements Algebra<Rational, MultiVarPolynomial> {
  public static final MultiVarPolynomial ZERO = new MultiVarPolynomial(Collections.emptyMap());
  public static final MultiVarPolynomial ONE =
      new MultiVarPolynomial(Collections.singletonMap(Term.CONST_TERM, Rational.ONE));

  private final Map<Term, Rational> monoids;

  private MultiVarPolynomial(Map<Term, Rational> monoids) {
    for (Rational val : monoids.values()) {
      checkArgument(!val.equals(Rational.ZERO), "term with 0 coef");
    }
    this.monoids = monoids;
  }

  public static MultiVarPolynomial valueOf(int i) {
    return valueOf(Rational.valueOf(i));
  }

  public static MultiVarPolynomial valueOf(Rational rational) {
    return new PolBuilder().add(Term.CONST_TERM, rational).build();
  }

  @Override
  public MultiVarPolynomial add(MultiVarPolynomial pol) {
    PolBuilder builder = new PolBuilder();
    for (Entry<Term, Rational> monoid : monoids.entrySet()) {
      builder.add(monoid.getKey(), monoid.getValue());
    }

    for (Entry<Term, Rational> monoid : pol.monoids.entrySet()) {
      builder.add(monoid.getKey(), monoid.getValue());
    }

    return builder.build();
  }

  @Override
  public MultiVarPolynomial negate() {
    return multiply(Rational.ONE.negate());
  }

  @Override
  public MultiVarPolynomial multiply(MultiVarPolynomial pol) {
    PolBuilder builder = new PolBuilder();
    for (Entry<Term, Rational> monoid : monoids.entrySet()) {
      for (Entry<Term, Rational> monoid2 : pol.monoids.entrySet()) {
        builder.add(
            monoid.getKey().multiply(monoid2.getKey()),
            monoid.getValue().multiply(monoid2.getValue()));
      }
    }

    return builder.build();
  }

  @Override
  public MultiVarPolynomial multiply(int i) {
    return this * Rational.valueOf(i);
  }

  @Override
  public MultiVarPolynomial add(int i) {
    return this + valueOf(i);
  }

  @Override
  public MultiVarPolynomial multiply(Rational r) {
    return multiply(new PolBuilder().add(new TermBuilder().build(), r).build());
  }

  @Override
  public MultiVarPolynomial divide(int i) {
    return this / Rational.valueOf(i);
  }

  public MultiVarPolynomial pow(int p) {
    checkArgument(p >= 0, "p < 0");
    MultiVarPolynomial retVal = MultiVarPolynomial.ONE;
    for (int i = 0; i < p; i++) {
      retVal = retVal.multiply(this);
    }
    return retVal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiVarPolynomial that = (MultiVarPolynomial) o;
    return Objects.equals(monoids, that.monoids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monoids);
  }

  public static final class PolBuilder {
    private final Map<Term, Rational> monoids = new HashMap<>();
    private boolean built;

    public PolBuilder add(Term term, Rational coef) {
      if (coef.equals(Rational.ZERO)) {
        return this;
      }
      if (monoids.containsKey(term)) {
        Rational newVal = monoids.get(term).add(coef);
        if (newVal.equals(Rational.ZERO)) {
          monoids.remove(term);
        } else {
          monoids.put(term, newVal);
        }
      } else {
        monoids.put(term, coef);
      }
      return this;
    }

    public MultiVarPolynomial build() {
      checkState(!built, "built");
      built = true;
      return new MultiVarPolynomial(monoids);
    }
  }

  public static final class Term {
    private static final Term CONST_TERM = new Term(Collections.emptyMap());

    private final Map<Integer, Integer> map;

    private Term(Map<Integer, Integer> map) {
      for (Entry<Integer, Integer> entry : map.entrySet()) {
        checkArgument(entry.getKey() >= 0, "varName < 0");
        checkArgument(entry.getValue() > 0, "degree <= 0");
      }
      this.map = map;
    }

    public int degreeOf(int i) {
      if (map.containsKey(i)) {
        return map.get(i);
      }
      return 0;
    }

    public Term multiply(Term t) {
      Map<Integer, Integer> newMap = new HashMap<>(map);

      for (Entry<Integer, Integer> entry : t.map.entrySet()) {
        int key = entry.getKey();
        int value = entry.getValue();
        if (newMap.containsKey(key)) {
          newMap.put(key, newMap.get(key) + value);
        } else {
          newMap.put(key, value);
        }
      }
      return new Term(newMap);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Term term = (Term) o;
      return Objects.equals(map, term.map);
    }

    @Override
    public int hashCode() {
      return Objects.hash(map);
    }
  }

  public static final class TermBuilder {
    private final Map<Integer, Integer> map = new HashMap<>();
    private boolean built;

    public TermBuilder pow(int var, int deg) {
      map.put(var, deg);
      return this;
    }

    public Term build() {
      checkState(!built, "built");
      built = true;
      return new Term(map);
    }
  }
}
