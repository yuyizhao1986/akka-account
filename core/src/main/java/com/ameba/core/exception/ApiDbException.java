package com.ameba.core.exception;

public class ApiDbException extends ApiException {

  public ApiDbException() {
    super();
  }

  public ApiDbException(Throwable cause) {
    super(cause);
  }


  @Override
  protected ResponseCode defaultResponseCode() {
    return ApiResponseCode.DB_ERROR;
  }
}