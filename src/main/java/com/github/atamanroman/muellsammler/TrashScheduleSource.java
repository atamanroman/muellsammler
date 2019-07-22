package com.github.atamanroman.muellsammler;

public interface TrashScheduleSource {

  TrashSchedule read(Address address);
  City city();
}
