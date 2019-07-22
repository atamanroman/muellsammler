package com.github.atamanroman.muellsammler;

import java.util.Objects;

public final class City {
  private final String name;

  public City(String name) {
    this.name = name.toUpperCase();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    City city = (City) o;
    return name.equals(city.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "City='" + name + "'";
  }
}
