package com.github.atamanroman.muellsammler.fuerth;

import static com.github.atamanroman.muellsammler.TestDataLoader.textFromClasspath;

class TestData {

  static final String SCHEDULE_CSV;
  static final String HOUSENUMBERS_JSON;

  static {
    SCHEDULE_CSV = textFromClasspath("fuerth/schedule.csv");
    HOUSENUMBERS_JSON = textFromClasspath("fuerth/housenumbers.json");
  }


}
