package xyz.realmath;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class AbelianGroupTest {
  private static final TestClass T1 = new TestClass();
  private static final TestClass T2 = new TestClass();
  private static final TestClass T3 = new TestClass();
  private static final TestClass T4 = new TestClass();

  private static final class TestClass implements AbelianGroup<TestClass> {

    @Override
    public TestClass add(TestClass t) {
      if (this == T1 && t == T3) {
        return T4;
      }
      return null;
    }

    @Override
    public TestClass negate() {
      if (this == T2) {
        return T3;
      }
      return null;
    }
  }

  @Test
  public void subtract() {
    assertSame(T4, T1.subtract(T2));
  }
}
