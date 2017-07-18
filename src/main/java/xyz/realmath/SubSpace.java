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
  private final Map<Tuple<Rational>, Integer> basis;

  private SubSpace(int embeddingDim, Map<Tuple<Rational>, Integer> basis) {
    checkArgument(embeddingDim >= 0, "embeddingDim < 0");
    this.embeddingDim = embeddingDim;
    this.basis = new HashMap<>(basis);
  }

  @SafeVarargs
  public static SubSpace span(Tuple<Rational>... vectors) {
    checkArgument(vectors.length > 0, "require at least one vector");
    SubSpace retVal = zeroDim(vectors[0].dim());
    retVal = retVal.spanWith(vectors[0]);
    for (Tuple<Rational> v : vectors) {
      checkNotNull(v, "vector");
      checkArgument(v.dim() == retVal.embeddingDim, "vectors with different dim");
      retVal = retVal.spanWith(v);
    }
    return retVal;
  }

  public static SubSpace zeroDim(int embeddingDim) {
    return new SubSpace(embeddingDim, Collections.emptyMap());
  }

  public Set<Tuple<Rational>> basis() {
    return Collections.unmodifiableSet(basis.keySet());
  }

  public boolean contains(Tuple<Rational> vector) {
    checkArgument(vector.dim() == embeddingDim, "vector.length != embedding dim");
    // TODO: ZERO
    return reduce(vector).equals(vector.multiply(Rational.ZERO));
  }

  private Tuple<Rational> reduce(Tuple<Rational> vector) {
    for (Map.Entry<Tuple<Rational>, Integer> entry : basis.entrySet()) {
      Integer idx = entry.getValue();
      Rational v = vector.get(idx);
      if (!v.equals(Rational.ZERO)) {
        vector = entry.getKey().multiply(v).multiply(Rational.ONE.negate()).add(vector);
      }
    }
    return vector;
  }

  private SubSpace spanWith(Tuple<Rational> vector) {
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
    Map<Tuple<Rational>, Integer> newBasis = new HashMap<>();
    newBasis.put(vector, i);

    for (Map.Entry<Tuple<Rational>, Integer> entry : basis.entrySet()) {
      Tuple<Rational> t1 = entry.getKey();
      Rational v = entry.getKey().get(i);
      if (!v.equals(Rational.ZERO)) {
        t1 = vector.multiply(v).multiply(Rational.ONE.negate()).add(entry.getKey());
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

    for (Tuple<Rational> vector : basis.keySet()) {
      if (!that.contains(vector)) {
        return false;
      }
    }

    for (Tuple<Rational> vector : that.basis.keySet()) {
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
