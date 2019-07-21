package com.github.atamanroman.muellsammler.fuerth;

import com.github.atamanroman.muellsammler.MuellSammlerException;

public class FuertTrashException extends MuellSammlerException {
	public FuertTrashException() {
	}

	public FuertTrashException(String message) {
		super(message);
	}

	public FuertTrashException(String message, Throwable cause) {
		super(message, cause);
	}

	public FuertTrashException(Throwable cause) {
		super(cause);
	}

	public FuertTrashException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
