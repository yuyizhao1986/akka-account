package com.ameba.core.exception;

public class Checker {
  public static void checkArgument(boolean expression, String errorMessage) {
    if (!expression) {
      throw new ApiParamException().enhance(errorMessage);
    }
  }

  public static void checkDbResult(boolean expression, String errorMessage) {
    if (!expression) {
      throw new ApiDbException().enhance(errorMessage);
    }
  }

  public static void check(boolean expression, ApiException exception) {
    if (!expression && exception != null) {
      throw exception;
    }
  }

  public static void check(boolean expression, ApiException exception, ResponseCode code) {
    if (!expression && exception != null) {
      exception.enhance(code);
      throw exception;
    }
  }

}
