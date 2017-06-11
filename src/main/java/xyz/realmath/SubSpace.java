package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.errorprone.annotations.Immutable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Immutable
public final class SubSpace {
  private final int embeddingDim;

  @SuppressWarnings("Immutable") // only written in constructor
  private final Map<Tuple, Integer> basis;

  private SubSpace(int embeddingDim, Map<Tuple, Integer> basis) {
    checkArgument(embeddingDim >= 0, "embeddingDim < 0");
    this.embeddingDim = embeddingDim;
    this.basis = basis;
  }

  public static SubSpace span(Tuple... vectors) {
    checkArgument(vectors.length > 0, "require at least one vector");
    SubSpace retVal = zeroDim(vectors[0].length);
    retVal = retVal.spanWith(vectors[0]);
    for (Tuple v : vectors) {
      checkNotNull(v, "vector");
      checkArgument(v.length == retVal.embeddingDim, "vectors with different dim");
      retVal = retVal.spanWith(v);
    }
    return retVal;
  }

  public static SubSpace zeroDim(int embeddingDim) {
    return new SubSpace(embeddingDim, Collections.emptyMap());
  }

  public Set<Tuple> basis() {
    return Collections.unmodifiableSet(basis.keySet());
  }

  public boolean contains(Tuple vector) {
    checkArgument(vector.length == embeddingDim, "vector.length != embedding dim");
    return reduce(vector).equals(vector.multiply(Rational.ZERO));
  }

  private Tuple reduce(Tuple vector) {
    for (Map.Entry<Tuple, Integer> entry : basis.entrySet()) {
      Integer idx = entry.getValue();
      Rational v = vector.get(idx);
      if (!v.equals(Rational.ZERO)) {
        vector = vector.minus(entry.getKey().multiply(v));
      }
    }
    return vector;
  }

  public SubSpace spanWith(Tuple vector) {
    vector = reduce(vector);

    // normalize vector
    int i = 0;
    for (Rational r : vector) {
      if (!r.equals(Rational.ZERO)) {
        vector = vector.multiply(r.reciprocal());
        break;
      }
      i++;
    }
    if (i == embeddingDim) {
      return this;
    }

    // normalize old base vectors
    Map<Tuple, Integer> newBasis = new HashMap<>();
    newBasis.put(vector, i);

    for (Map.Entry<Tuple, Integer> entry : basis.entrySet()) {
      Tuple t1 = entry.getKey();
      Rational v = entry.getKey().get(i);
      if (!v.equals(Rational.ZERO)) {
        t1 = entry.getKey().minus(vector.multiply(v));
      }
      newBasis.put(t1, entry.getValue());
    }
    return new SubSpace(embeddingDim, newBasis);
  }

  public int dim() {
    return basis.size();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SubSpace that = (SubSpace) obj;
    if (that.embeddingDim != embeddingDim) {
      return false;
    }

    for (Tuple vector : basis.keySet()) {
      if (!that.contains(vector)) {
        return false;
      }
    }

    for (Tuple vector : that.basis.keySet()) {
      if (!contains(vector)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(embeddingDim, dim());
  }
}
