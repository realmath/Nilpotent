package xyz.realmath;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xyz.realmath.StructureConstants.Builder;
import xyz.realmath.StructureConstants.JacobiException;

@RunWith(JUnit4.class)
public class StructureConstantsTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  private static void jacobi(StructureConstants sc) {
    int dim = sc.getDim();
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
      assertEquals("Jacobi identity not satisfied.", je.getMessage());
      verifyJacobiException(builder.buildForTest(), je.i, je.j, je.k);
    }
  }

  private static void verifyJacobiException(StructureConstants sc, int i, int j, int k) {
    int dim = sc.getDim();
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
      StructureConstants sc, Rational[] x, Rational[] y, Rational[] z) {
    int dim = sc.getDim();
    Rational[] retVal = new Rational[dim];

    Rational[] jacobi1 = sc.lieBracket(sc.lieBracket(x, y), z);
    Rational[] jacobi2 = sc.lieBracket(sc.lieBracket(y, z), x);
    Rational[] jacobi3 = sc.lieBracket(sc.lieBracket(z, x), y);
    for (int m = 0; m < dim; m++) {
      retVal[m] = jacobi1[m].add(jacobi2[m]).add(jacobi3[m]);
    }
    return retVal;
  }

  @Test
  public void dim() {
    StructureConstants structureConstants = new Builder(3).build();
    assertEquals(3, structureConstants.getDim());
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
    StructureConstants sc = new Builder(3).add(0, 1, 2, Rational.ONE).build();
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
    StructureConstants sc =
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
  }
}
