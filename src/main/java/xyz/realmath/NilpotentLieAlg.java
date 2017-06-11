package xyz.realmath;

import com.google.errorprone.annotations.Immutable;

@Immutable
public class NilpotentLieAlg {
  private final MalcevStructureConstants structureConstants;

  public NilpotentLieAlg(MalcevStructureConstants structureConstants) {
    this.structureConstants = structureConstants;
  }

  public Tuple lieBracket(Tuple left, Tuple right) {
    return structureConstants.lieBracket(left, right);
  }

  public int dim() {
    return structureConstants.dim();
  }
}
