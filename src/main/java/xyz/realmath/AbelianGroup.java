package xyz.realmath;

public interface AbelianGroup<T extends AbelianGroup<T>> {
  default boolean isZero() {
    return this.equals(this.add(thisT()));
  }

  T add(T t);

  T negate();

  default T subtract(T t) {
    return thisT().add(t.negate());
  }

  @SuppressWarnings("unchecked")
  default T thisT() {
    return (T) this;
  }
}
