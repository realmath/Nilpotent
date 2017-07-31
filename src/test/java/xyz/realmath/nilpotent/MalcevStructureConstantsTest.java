package xyz.realmath.nilpotent;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xyz.realmath.ArrayTuple;
import xyz.realmath.Rational;
import xyz.realmath.Tuple;
import xyz.realmath.nilpotent.MalcevStructureConstants.Builder;
import xyz.realmath.nilpotent.MalcevStructureConstants.JacobiException;

@RunWith(JUnit4.class)
public class MalcevStructureConstantsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  private static void jacobi(MalcevStructureConstants sc) {
    int dim = sc.dim();
    for (int i = 0; i < dim; i++) {
      Rational[] xi = baseVector(dim, i);
      for (int j = 0; j < dim; j++) {
        Rational[] xj = baseVector(dim, j);

        for (int k = 0; k < dim; k++) {
          Rational[] xk = baseVector(dim, k);

          Rational[] jacobiCycle = jacobiCycle(sc, xi, xj, xk);

          assertArrayEquals(zeroVector(dim), jacobiCycle);
        }
      }
    }
  }

  private static void justifyInvalidBuilder(Builder builder) {
    try {
      builder.build();
      fail();
    } catch (JacobiException je) {
      assertThat(je.getMessage(), StringContains.containsString("Jacobi identity not satisfied: "));
      verifyJacobiException(builder.buildForTest(), je.i, je.j, je.k);
    }
  }

  private static void verifyJacobiException(MalcevStructureConstants sc, int i, int j, int k) {
    int dim = sc.dim();
    Rational[] xi = baseVector(dim, i);
    Rational[] xj = baseVector(dim, j);
    Rational[] xk = baseVector(dim, k);

    Rational[] jacobiCycle = jacobiCycle(sc, xi, xj, xk);

    assertThat(jacobiCycle, not(equalTo(zeroVector(dim))));
  }

  private static Rational[] baseVector(int dim, int idx) {
    Rational[] retVal = zeroVector(dim);
    retVal[idx] = Rational.ONE;
    return retVal;
  }

  private static Rational[] zeroVector(int dim) {
    Rational[] retVal = new Rational[dim];
    for (int m = 0; m < dim; m++) {
      retVal[m] = Rational.ZERO;
    }
    return retVal;
  }

  private static Rational[] jacobiCycle(
      MalcevStructureConstants sc, Rational[] x, Rational[] y, Rational[] z) {
    int dim = sc.dim();
    Rational[] retVal = new Rational[dim];

    ArrayTuple<Rational> tx = new ArrayTuple<>(x);
    ArrayTuple<Rational> ty = new ArrayTuple<>(y);
    ArrayTuple<Rational> tz = new ArrayTuple<>(z);

    Tuple<Rational> jacobi1 = sc.lieBracket(sc.lieBracket(tx, ty), tz);
    Tuple<Rational> jacobi2 = sc.lieBracket(sc.lieBracket(ty, tz), tx);
    Tuple<Rational> jacobi3 = sc.lieBracket(sc.lieBracket(tz, tx), ty);
    for (int m = 0; m < dim; m++) {
      retVal[m] = jacobi1.get(m).add(jacobi2.get(m)).add(jacobi3.get(m));
    }
    return retVal;
  }

  @Test
  public void dim() {
    MalcevStructureConstants structureConstants = new Builder(3).build();
    assertEquals(3, structureConstants.dim());
  }

  @Test
  public void dimIllegal() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("dim >= 0");
    new Builder(-3);
  }

  @Test
  public void addIllegalI() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("i >= 0");
    new Builder(3).add(-1, 1, 2, Rational.ZERO);
  }

  @Test
  public void addIllegalIJ() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("i < j");
    new Builder(3).add(1, 1, 2, Rational.ZERO);
  }

  @Test
  public void addIllegalJK() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("j < k");
    new Builder(3).add(1, 2, 2, Rational.ZERO);
  }

  @Test
  public void addIllegalK() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("k < dim");
    new Builder(3).add(1, 2, 3, Rational.ZERO);
  }

  @Test
  public void addDuplicate() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Structure constant entry i, j, k already exists.");

    new Builder(3).add(0, 1, 2, Rational.ZERO).add(0, 1, 2, Rational.ONE);
  }

  @Test
  public void heisenberg() {
    MalcevStructureConstants sc = new Builder(3).add(0, 1, 2, Rational.ONE).build();
    assertEquals(Rational.ZERO, sc.get(0, 0, 0));
    assertEquals(Rational.ZERO, sc.get(0, 0, 1));
    assertEquals(Rational.ZERO, sc.get(0, 0, 2));
    assertEquals(Rational.ZERO, sc.get(0, 1, 0));
    assertEquals(Rational.ZERO, sc.get(0, 1, 1));
    assertEquals(Rational.ONE, sc.get(0, 1, 2));
    assertEquals(Rational.ZERO, sc.get(0, 2, 0));
    assertEquals(Rational.ZERO, sc.get(0, 2, 1));
    assertEquals(Rational.ZERO, sc.get(0, 2, 2));

    assertEquals(Rational.ZERO, sc.get(1, 0, 0));
    assertEquals(Rational.ZERO, sc.get(1, 0, 1));
    assertEquals(Rational.ONE.negate(), sc.get(1, 0, 2));
    assertEquals(Rational.ZERO, sc.get(1, 1, 0));
    assertEquals(Rational.ZERO, sc.get(1, 1, 1));
    assertEquals(Rational.ZERO, sc.get(1, 1, 2));
    assertEquals(Rational.ZERO, sc.get(1, 2, 0));
    assertEquals(Rational.ZERO, sc.get(1, 2, 1));
    assertEquals(Rational.ZERO, sc.get(1, 2, 2));

    assertEquals(Rational.ZERO, sc.get(2, 0, 0));
    assertEquals(Rational.ZERO, sc.get(2, 0, 1));
    assertEquals(Rational.ZERO, sc.get(2, 0, 2));
    assertEquals(Rational.ZERO, sc.get(2, 1, 0));
    assertEquals(Rational.ZERO, sc.get(2, 1, 1));
    assertEquals(Rational.ZERO, sc.get(2, 1, 2));
    assertEquals(Rational.ZERO, sc.get(2, 2, 0));
    assertEquals(Rational.ZERO, sc.get(2, 2, 1));
    assertEquals(Rational.ZERO, sc.get(2, 2, 2));
  }

  @Test
  public void validCases() {
    MalcevStructureConstants sc =
        new Builder(4).add(0, 1, 2, Rational.ONE).add(1, 2, 3, Rational.ONE).build();
    jacobi(sc);

    sc =
        new Builder(4)
            .add(0, 1, 2, Rational.ONE)
            .add(1, 2, 3, Rational.ONE)
            .add(0, 2, 3, Rational.ONE)
            .build();
    jacobi(sc);

    sc =
        new Builder(4)
            .add(0, 1, 2, Rational.ONE)
            .add(1, 2, 3, Rational.ONE)
            .add(0, 2, 3, Rational.ONE)
            .add(0, 1, 3, Rational.ONE)
            .build();
    jacobi(sc);

    sc =
        new Builder(5)
            .add(0, 1, 2, Rational.ONE)
            .add(1, 2, 3, Rational.ONE)
            .add(0, 2, 4, Rational.ONE)
            .add(1, 3, 4, Rational.ONE)
            .build();
    jacobi(sc);

    sc = new Builder(5).add(0, 1, 4, Rational.ONE).add(2, 3, 4, Rational.ONE).build();
    jacobi(sc);

    sc = new Builder(5).add(0, 1, 3, Rational.ONE).add(0, 2, 4, Rational.ONE).build();
    jacobi(sc);

    sc =
        new Builder(5)
            .add(0, 1, 3, Rational.ONE)
            .add(0, 3, 4, Rational.ONE)
            .add(1, 2, 4, Rational.ONE)
            .build();
    jacobi(sc);

    sc =
        new Builder(5)
            .add(0, 1, 2, Rational.ONE)
            .add(0, 2, 3, Rational.ONE)
            .add(1, 2, 4, Rational.ONE)
            .build();
    jacobi(sc);

    sc =
        new Builder(5)
            .add(0, 1, 2, Rational.ONE)
            .add(0, 2, 3, Rational.ONE)
            .add(0, 3, 4, Rational.ONE)
            .build();
    jacobi(sc);

    sc =
        new Builder(5)
            .add(0, 1, 2, Rational.ONE)
            .add(0, 2, 3, Rational.ONE)
            .add(0, 3, 4, Rational.ONE)
            .add(1, 2, 4, Rational.ONE)
            .build();
    jacobi(sc);
  }

  @Test
  public void invalidCases() {
    Builder builder =
        new Builder(5)
            .add(0, 1, 2, Rational.ONE)
            .add(1, 2, 3, Rational.ONE)
            .add(0, 2, 4, Rational.ONE)
            .add(1, 3, 4, Rational.ONE)
            .add(2, 3, 4, Rational.ONE);
    justifyInvalidBuilder(builder);

    builder =
        new Builder(5)
            .add(0, 1, 4, Rational.ONE)
            .add(1, 2, 3, Rational.ONE)
            .add(0, 2, 3, Rational.ONE)
            .add(1, 3, 4, Rational.ONE);
    justifyInvalidBuilder(builder);

    builder =
        new Builder(7)
            .add(0, 1, 4, Rational.ONE)
            .add(2, 3, 5, Rational.ONE)
            .add(4, 5, 6, Rational.ONE);
    justifyInvalidBuilder(builder);

    builder =
        new Builder(7)
            .add(0, 1, 3, Rational.ONE)
            .add(0, 2, 5, Rational.ONE.negate())
            .add(0, 4, 6, Rational.ONE.negate())
            .add(1, 2, 4, Rational.ONE)
            .add(1, 5, 6, Rational.ONE.negate())
            .add(2, 3, 6, Rational.ONE);
    justifyInvalidBuilder(builder);
  }

  @Test
  public void moreValidCases() {
    MalcevStructureConstants sc =
        new Builder(7)
            .add(0, 1, 2, Rational.ONE)
            .add(1, 2, 6, Rational.ONE)
            .add(3, 4, 5, Rational.ONE)
            .add(3, 5, 6, Rational.ONE)
            .add(0, 4, 6, Rational.ONE)
            .build();
    jacobi(sc);

    sc =
        new Builder(7)
            .add(0, 1, 3, Rational.ONE)
            .add(0, 2, 5, Rational.ONE.negate())
            .add(0, 4, 6, Rational.ONE.negate())
            .add(1, 2, 4, Rational.ONE)
            .add(1, 5, 6, Rational.ONE.negate())
            .add(2, 3, 6, Rational.valueOf(2))
            .build();
    jacobi(sc);
  }
}
