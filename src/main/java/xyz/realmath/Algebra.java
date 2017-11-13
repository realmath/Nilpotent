package xyz.realmath;

public interface Algebra<F extends Field<F>, T extends Algebra<F, T>> extends Ring<T> {

  T multiply(F f);

  default T divide(F f) {
    return this.multiply(f.reciprocal());
  }

  T divide(int i);
}
