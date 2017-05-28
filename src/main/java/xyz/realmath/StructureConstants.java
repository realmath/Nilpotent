package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Immutable
public final class StructureConstants {
  private final int dim;
  private final ImmutableMap<Triple, Rational> map;

  private StructureConstants(int dim, ImmutableMap<Triple, Rational> map) {
    this.dim = dim;
    this.map = map;
  }

  public Rational get(int i, int j, int k) {
    checkArgument(i < dim, "i < dim");
    checkArgument(j < dim, "j < dim");
    checkArgument(k < dim, "k < dim");

    if (j > k) {
      return Rational.ZERO;
    }
    if (i > j) {
      return get(j, i, k).negate();
    }
    if (i == j) {
      return Rational.ZERO;
    }

    Triple triple = new Triple(i, j, k);
    if (map.containsKey(triple)) {
      return map.get(triple);
    }
    return Rational.ZERO;
  }

  public int getDim() {
    return dim;
  }

  public Rational[] lieBracket(Rational[] left, Rational[] right) {
    checkNotNull(left, "left");
    checkNotNull(right, "right");
    checkArgument(left.length == dim, "left.length != dim");
    checkArgument(right.length == dim, "right.length != dim");

    Rational[] retVal = new Rational[dim];

    for (int k = 0; k < dim; k++) {
      retVal[k] = Rational.ZERO;
      for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
          retVal[k] = retVal[k].add(left[i].multiply(right[j]).multiply(get(i, j, k)));
        }
      }
    }

    return retVal;
  }

  private void validate() {
    for (int i = 0; i < dim; i++) {
      for (int j = i + 1; j < dim; j++) {
        for (int k = j + 1; k < dim; k++) {
          for (int m = 0; m < dim; m++) {
            Rational sum = Rational.ZERO;
            for (int l = 0; l < dim; l++) {
              sum =
                  sum.add(get(j, k, l).multiply(get(l, i, m)))
                      .add(get(i, j, l).multiply(get(l, k, m)))
                      .add(get(k, i, l).multiply(get(l, j, m)));
            }
            if (!sum.equals(Rational.ZERO)) {
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
      super("Jacobi identity not satisfied.");
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

    public StructureConstants build() {
      StructureConstants retVal = new StructureConstants(dim, ImmutableMap.copyOf(map));
      retVal.validate();
      return retVal;
    }

    @VisibleForTesting
    StructureConstants buildForTest() {
      StructureConstants retVal = new StructureConstants(dim, ImmutableMap.copyOf(map));
      return retVal;
    }
  }

  @Immutable
  private static class Triple {
    final int i;
    final int j;
    final int k;

    Triple(int i, int j, int k) {
      this.i = i;
      this.j = j;
      this.k = k;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      Triple that = (Triple) obj;
      return i == that.i && j == that.j && k == that.k;
    }

    @Override
    public int hashCode() {
      return Objects.hash(i, j, k);
    }
  }
}
