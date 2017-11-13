package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.errorprone.annotations.Immutable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

@Immutable
public class ArrayTuple<T extends Algebra<Rational, T>> implements Tuple<T> {

  @SuppressWarnings("Immutable") // only written in constructor
  private final ArrayList<T> ts = new ArrayList<>();

  private final int length;

  @SafeVarargs
  public ArrayTuple(T... ts) {
    this.length = ts.length;

    for (T t : ts) {
      checkNotNull(t, "ts");
      this.ts.add(t);
    }
  }

  public ArrayTuple(ArrayList<T> ts) {
    this.length = ts.size();

    for (T t : ts) {
      checkNotNull(t, "ts");
      this.ts.add(t);
    }
  }

  @Override
  public int dim() {
    return length;
  }

  @Override
  public T get(int idx) {
    return ts.get(idx);
  }

  @Override
  public Tuple<T> add(Tuple<T> tuple) {
    checkArgument(tuple.dim() == length, "tuple.length != length");

    ArrayList<T> sums = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      sums.add(ts.get(i).add(tuple.get(i)));
    }

    return new ArrayTuple<>(sums);
  }

  @Override
  public Tuple<T> subtract(Tuple<T> tuple) {

    checkArgument(tuple.dim() == length, "tuple.length != length");

    ArrayList<T> sums = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      sums.add(ts.get(i).subtract(tuple.get(i)));
    }

    return new ArrayTuple<>(sums);
  }

  @Override
  public Tuple<T> negate() {
    ArrayList<T> retVals = new ArrayList<>();
    for (T t : ts) {
      // TODO: fix javaoo
      retVals.add(t.negate());
    }
    return new ArrayTuple<>(retVals);
  }

  @Override
  public Tuple<T> multiply(Rational r) {
    ArrayList<T> prods = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      prods.add(ts.get(i).multiply(r));
    }

    return new ArrayTuple<>(prods);
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int i;

      @Override
      public boolean hasNext() {
        return i < length;
      }

      @Override
      public T next() {
        i++;
        if (i > ts.size()) {
          throw new NoSuchElementException();
        }
        return ts.get(i - 1);
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
    ArrayTuple<?> ts1 = (ArrayTuple<?>) obj;
    return Objects.equals(ts, ts1.ts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ts);
  }
}
