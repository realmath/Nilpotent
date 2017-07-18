package xyz.realmath;

import com.google.errorprone.annotations.Immutable;
import java.util.Objects;

@Immutable
public class Triple {
  public final int i;
  public final int j;
  public final int k;

  public Triple(int i, int j, int k) {
    this.i = i;
    this.j = j;
    this.k = k;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Triple that = (Triple) obj;
    return i == that.i && j == that.j && k == that.k;
  }

  @Override
  public int hashCode() {
    return Objects.hash(i, j, k);
  }
}
