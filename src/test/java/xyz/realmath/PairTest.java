package xyz.realmath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PairTest {

  @Test
  public void equals() throws Exception {
    Pair p1 = new Pair(1, 2);
    Pair p2 = new Pair(1, 2);
    Pair p3 = new Pair(1, 3);
    assertEquals(p1, p2);
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1, p3);
  }
}
