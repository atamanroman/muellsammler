package com.github.atamanroman.muellsammler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PickUp {

  private TrashType type;
  private LocalDate date;
  @Nullable
  private LocalTime time;

  public PickUp(TrashType type, LocalDate date, @NotNull LocalTime time) {
    this.type = type;
    this.date = date;
    this.time = time;
  }

  public PickUp(TrashType type, LocalDate date) {
    this.type = type;
    this.date = date;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PickUp pickUp = (PickUp) o;
    return type == pickUp.type && date.equals(pickUp.date) && Objects.equals(time, pickUp.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, date, time);
  }
}
