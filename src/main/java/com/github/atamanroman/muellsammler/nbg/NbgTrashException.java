package com.github.atamanroman.muellsammler.nbg;

import com.github.atamanroman.muellsammler.MuellSammlerException;

public class NbgTrashException extends MuellSammlerException {
  public NbgTrashException() {
  }

  public NbgTrashException(String message) {
    super(message);
  }

  public NbgTrashException(String message, Throwable cause) {
    super(message, cause);
  }

  public NbgTrashException(Throwable cause) {
    super(cause);
  }

  public NbgTrashException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
