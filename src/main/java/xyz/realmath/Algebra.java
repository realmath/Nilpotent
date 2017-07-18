package xyz.realmath;

import xyz.realmath.nilpotent.Field;

public interface Algebra<F extends Field<F>, T extends Algebra<F, T>> {
  T add(T t);

  T negate();

  T multiply(T t);

  T multiply(F r);
}
