package xyz.realmath.nilpotent;

import com.google.errorprone.annotations.Immutable;
import xyz.realmath.Algebra;
import xyz.realmath.Rational;
import xyz.realmath.Tuple;

@Immutable
public class NilpotentLieAlg {
  private final MalcevStructureConstants structureConstants;

  public NilpotentLieAlg(MalcevStructureConstants structureConstants) {
    this.structureConstants = structureConstants;
  }

  public <T extends Algebra<Rational, T>> Tuple<T> lieBracket(Tuple<T> left, Tuple<T> right) {
    return structureConstants.lieBracket(left, right);
  }

  public int dim() {
    return structureConstants.dim();
  }
}
