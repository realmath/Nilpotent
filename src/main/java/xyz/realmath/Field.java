package xyz.realmath;

public interface Field<T extends Field<T>> extends Algebra<T, T> {
  default boolean isOne() {
    return this.equals(this * thisT());
  }

  @Override
  T multiply(T f);

  T reciprocal();
}
