package com.yourlhub.domain.exceptions;

import com.yourlhub.domain.enums.ErrorTypes;
import java.util.Map;

public class GraphqlException extends RuntimeException {

  private final ErrorTypes errorTypes;

  private final String message;

  private final Map<String, Object> extensions;

  public GraphqlException(ErrorTypes errorTypes, String message, Map<String, Object> extensions) {
    super(message);
    this.errorTypes = errorTypes;
    this.message = message;
    this.extensions = extensions;
  }

  public ErrorTypes getErrorTypes() {
    return errorTypes;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Map<String, Object> getExtensions() {
    return extensions;
  }
}
