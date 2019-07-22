package com.github.atamanroman.muellsammler.fuerth;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestData {

  public static final String SCHEDULE_CSV;
  public static final String HOUSENUMBERS_JSON;

  static {
    SCHEDULE_CSV = textFromClasspath("schedule.csv");
    HOUSENUMBERS_JSON = textFromClasspath("housenumbers.json");
  }

  private static String textFromClasspath(String src) {
    try {
      var is = FuerthTrashApiTest.class.getResourceAsStream(src);
      return new String(is.readAllBytes(), Charset.forName("UTF-8"));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load testdata");
    }
  }

}
