package com.github.atamanroman.muellsammler.infra;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Forces null checks per package. Does not descend down the hierarchy.
 */
@Retention(RetentionPolicy.CLASS)
public @interface NonNullApi {
}
