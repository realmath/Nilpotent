package xyz.realmath.nilpotent;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xyz.realmath.ArrayTuple;
import xyz.realmath.Rational;
import xyz.realmath.RationalArrays;
import xyz.realmath.Tuple;

@RunWith(JUnit4.class)
public class NilpotentLieAlgTest {
  @Test
  public void multiply() {
    MalcevStructureConstants sc =
        new MalcevStructureConstants.Builder(3).add(0, 1, 2, Rational.ONE).build();
    ArrayTuple<Rational> t1 = new ArrayTuple<>(RationalArrays.base(3, 0));
    ArrayTuple<Rational> t2 = new ArrayTuple<>(RationalArrays.base(3, 1));
    NilpotentLieAlg n = new NilpotentLieAlg(sc);

    Tuple<Rational> t3 = n.lieBracket(t1, t2);
    assertEquals(sc.lieBracket(t1, t2), t3);
  }

  @Test
  public void dim() {
    MalcevStructureConstants sc = new MalcevStructureConstants.Builder(3).build();
    NilpotentLieAlg alg = new NilpotentLieAlg(sc);
    assertEquals(3, alg.dim());
  }
}
