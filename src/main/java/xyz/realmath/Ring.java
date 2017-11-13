package xyz.realmath;

/** Unital Ring. */
public interface Ring<T extends Ring<T>> extends AbelianGroup<T> {
  T add(int i);

  default T subtract(int i) {
    return this.add(-i);
  }

  T multiply(T t);

  T multiply(int i);
}
