package com.github.atamanroman.muellsammler;

import java.util.EnumSet;
import java.util.Set;

public interface TrashScheduleSource {

  TrashSchedule read(Address address);

  City city();

  default Set<TrashType> supports() {
    return Set.of(TrashType.values());
  }

}
