package xyz.realmath;

public interface AbelianGroup<T extends AbelianGroup<T>> {
  default boolean isZero() {
    return this.equals(this + thisT());
  }

  T add(T t);

  T negate();

  default T subtract(T t) {
    return this + (-t);
  }

  @SuppressWarnings("unchecked")
  default T thisT() {
    return (T) this;
  }
}
