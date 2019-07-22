package com.github.atamanroman.muellsammler;

public class MuellSammlerException extends RuntimeException {

  public MuellSammlerException() {
  }

  public MuellSammlerException(String message) {
    super(message);
  }

  public MuellSammlerException(String message, Throwable cause) {
    super(message, cause);
  }

  public MuellSammlerException(Throwable cause) {
    super(cause);
  }

  public MuellSammlerException(
    String message, Throwable cause,
    boolean enableSuppression,
    boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
