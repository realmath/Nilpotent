package xyz.realmath.nilpotent;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import xyz.realmath.Algebra;
import xyz.realmath.ArrayTuple;
import xyz.realmath.Rational;
import xyz.realmath.Triple;
import xyz.realmath.Tuple;

/** Structure constants of a nilpotent Lie algebra in a Malcev basis. */
@Immutable
public class MalcevStructureConstants {
  private final int dim;
  private final ImmutableMap<Triple, Rational> map;

  private MalcevStructureConstants(int dim, ImmutableMap<Triple, Rational> map) {
    this.dim = dim;
    this.map = map;
  }

  Rational get(int i, int j, int k) {
    checkArgument(i < dim, "i < dim");
    checkArgument(j < dim, "j < dim");
    checkArgument(k < dim, "k < dim");

    if (j > k) {
      return Rational.Companion.getZERO();
    }
    if (i > j) {
      return get(j, i, k).negate();
    }
    if (i == j) {
      return Rational.Companion.getZERO();
    }

    Triple triple = new Triple(i, j, k);
    if (map.containsKey(triple)) {
      return map.get(triple);
    }
    return Rational.Companion.getZERO();
  }

  int dim() {
    return dim;
  }

  <T extends Algebra<Rational, T>> Tuple<T> lieBracket(Tuple<T> left, Tuple<T> right) {
    checkArgument(left.dim() == dim, "left.length != dim");
    checkArgument(right.dim() == dim, "right.length != dim");

    ArrayList<T> retVals = new ArrayList<>();

    for (int k = 0; k < dim; k++) {
      for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
          T summand = left.get(i).multiply(right.get(j)).multiply(get(i, j, k));
          checkState(retVals.size() >= k);
          if (retVals.size() == k) {
            retVals.add(summand);
          } else {
            retVals.set(k, retVals.get(k).add(summand));
          }
        }
      }
    }

    return new ArrayTuple<T>(retVals);
  }

  private void validate() {
    for (int i = 0; i < dim; i++) {
      for (int j = i + 1; j < dim; j++) {
        for (int k = j + 1; k < dim; k++) {
          for (int m = 0; m < dim; m++) {
            Rational sum = Rational.Companion.getZERO();
            for (int l = 0; l < dim; l++) {
              sum =
                  sum.add(get(j, k, l).multiply(get(l, i, m)))
                      .add(get(i, j, l).multiply(get(l, k, m)))
                      .add(get(k, i, l).multiply(get(l, j, m)));
            }
            if (!sum.equals(Rational.Companion.getZERO())) {
              throw new JacobiException(i, j, k);
            }
          }
        }
      }
    }
  }

  @VisibleForTesting
  static final class JacobiException extends IllegalStateException {
    final int i;
    final int j;
    final int k;

    JacobiException(int i, int j, int k) {
      super("Jacobi identity not satisfied: " + i + ", " + j + ", " + k);
      this.i = i;
      this.j = j;
      this.k = k;
    }
  }

  public static final class Builder {
    private final int dim;
    private final Map<Triple, Rational> map = new HashMap<>();

    public Builder(int dim) {
      checkArgument(dim >= 0, "dim >= 0");
      this.dim = dim;
    }

    public Builder add(int i, int j, int k, Rational value) {
      checkArgument(i >= 0, "i >= 0");
      checkArgument(i < j, "i < j");
      checkArgument(j < k, "j < k");
      checkArgument(k < dim, "k < dim");

      Triple triple = new Triple(i, j, k);
      checkArgument(!map.containsKey(triple), "Structure constant entry i, j, k already exists.");
      map.put(triple, value);
      return this;
    }

    public MalcevStructureConstants build() {
      MalcevStructureConstants retVal = new MalcevStructureConstants(dim, ImmutableMap.copyOf(map));
      retVal.validate();
      return retVal;
    }

    @VisibleForTesting
    MalcevStructureConstants buildForTest() {
      return new MalcevStructureConstants(dim, ImmutableMap.copyOf(map));
    }
  }
}
