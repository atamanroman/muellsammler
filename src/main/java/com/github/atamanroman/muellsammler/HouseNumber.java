package com.github.atamanroman.muellsammler;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public final class HouseNumber {

  private final String number;

  public HouseNumber(String number) {
    this.number = number.trim().replaceAll(" ", "");
  }

  public String getNumber() {
    return number;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HouseNumber that = (HouseNumber) o;
    return number.equals(that.number);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number);
  }

  @Override
  public String toString() {
    return "HouseNumber='" + number + "'";
  }
}
