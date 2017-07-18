package xyz.realmath;

import java.util.Objects;

public class Pair {
  public final int i;
  public final int j;

  public Pair(int i, int j) {
    this.i = i;
    this.j = j;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Pair that = (Pair) obj;
    return i == that.i && j == that.j;
  }

  @Override
  public int hashCode() {
    return Objects.hash(i, j);
  }
}
