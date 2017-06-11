package xyz.realmath;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultiVarPolynomialFun {
  private final Map<ArrayList<Integer>, Rational> monoids;
  private final int dim;

  private MultiVarPolynomialFun(int dim, Map<ArrayList<Integer>, Rational> monoids) {
    this.dim = dim;
    this.monoids = monoids;
  }

  public Rational evaluate(Tuple tuple) {
    Rational retVal = Rational.ZERO;
    for (Map.Entry<ArrayList<Integer>, Rational> entry : monoids.entrySet()) {
      Rational monoidVal = entry.getValue();
      for (int i = 0; i < dim; i++) {
        monoidVal = monoidVal.multiply(tuple.get(i).pow(entry.getKey().get(i)));
      }
      retVal = retVal.add(monoidVal);
    }
    return retVal;
  }

  public MultiVarPolynomialFun add(MultiVarPolynomialFun pol) {
    checkArgument(pol.dim == dim, "pol.dim != this.dim");

    Builder builder = new Builder(dim);
    for (Map.Entry<ArrayList<Integer>, Rational> entry : monoids.entrySet()) {
      builder.addMonoid(entry.getKey(), entry.getValue());
    }
    for (Map.Entry<ArrayList<Integer>, Rational> entry : pol.monoids.entrySet()) {
      builder.addMonoid(entry.getKey(), entry.getValue());
    }

    return builder.build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiVarPolynomialFun that = (MultiVarPolynomialFun) o;
    return dim == that.dim && Objects.equals(monoids, that.monoids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monoids, dim);
  }

  public static final class Builder {
    private final int dim;
    private final Map<ArrayList<Integer>, Rational> monoids = new HashMap<>();

    public Builder(int dim) {
      this.dim = dim;
    }

    public Builder addMonoid(List<Integer> orders, Rational value) {
      checkArgument(orders.size() == dim, "orders.size() != dim");

      if (value.equals(Rational.ZERO)) {
        return this;
      }

      ArrayList<Integer> ordersCopy = new ArrayList<>(orders);
      if (monoids.containsKey(ordersCopy)) {
        Rational newValue = monoids.get(ordersCopy).add(value);
        if (newValue.equals(Rational.ZERO)) {
          monoids.remove(ordersCopy);
        } else {
          monoids.put(ordersCopy, newValue);
        }
      } else {
        monoids.put(ordersCopy, value);
      }

      return this;
    }

    public MultiVarPolynomialFun build() {
      return new MultiVarPolynomialFun(dim, new HashMap<>(monoids));
    }
  }
}
