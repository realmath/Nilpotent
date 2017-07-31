package xyz.realmath;

public interface Tuple<T extends Algebra<Rational, T>> extends Iterable<T> {
  T get(int idx);

  Tuple<T> add(Tuple<T> tuple);

  Tuple<T> multiply(Rational r);

  Tuple<T> subtract(Tuple<T> tuple);

  Tuple<T> negate();

  int dim();
}
