package xyz.realmath.nilpotent;

public interface Field<F extends Field<F>> {

  default boolean isZero() {
    return this.equals(add(thisT()));
  }

  default boolean isOne() {
    return this.equals(multiply(thisT()));
  }

  F add(F f);

  F negate();

  default F minus(F f) {
    return this.add(f.negate());
  }

  F multiply(F f);

  F reciprocal();

  default F dividedBy(F f) {
    return this.multiply(f.reciprocal());
  }

  @SuppressWarnings("unchecked")
  default F thisT() {
    return (F) this;
  }
}
