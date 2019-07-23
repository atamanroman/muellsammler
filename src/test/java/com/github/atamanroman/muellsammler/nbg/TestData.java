package com.github.atamanroman.muellsammler.nbg;

import static com.github.atamanroman.muellsammler.TestDataLoader.bytesFromClasspath;

class TestData {
  public static final byte[] GELBER_SACK_PDF;

  static {
    GELBER_SACK_PDF = bytesFromClasspath("nbg/gelber_sack_2017-19.pdf");
  }
}
