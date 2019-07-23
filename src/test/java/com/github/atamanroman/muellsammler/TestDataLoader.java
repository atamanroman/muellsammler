package com.github.atamanroman.muellsammler;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Loads everything relative to com.github.atamanroman.muellsammler
 */
public class TestDataLoader {

  public static String textFromClasspath(String src) {
    var bytes = bytesFromClasspath(src);
    return new String(bytes, Charset.forName("UTF-8"));
  }

  public static byte[] bytesFromClasspath(String src) {
    try {
      var is = TestDataLoader.class.getResourceAsStream(src);
      byte[] bytes;
      if (is == null || (bytes = is.readAllBytes()) == null || bytes.length == 0) {
        throw new IllegalArgumentException("Could not read from src=" + src);
      }
      return bytes;
    } catch (IOException e) {
      throw new IllegalStateException("Could not read from src=" + src, e);
    }
  }
}
