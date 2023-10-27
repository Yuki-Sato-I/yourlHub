package com.yourlhub.domain.exceptions;

import com.yourlhub.domain.enums.ErrorTypes;
import java.util.Collections;
import java.util.Map;

public class ApplicationErrors {

  public static GraphqlException userNotFound(final String key) {
    return new GraphqlException(
        ErrorTypes.BAD_REQUEST, "this user is not found.", Map.of("key", key));
  }

  public static GraphqlException invalidJwt() {
    return new GraphqlException(
        ErrorTypes.BAD_REQUEST, "failed jwt process.", Collections.emptyMap());
  }
}
