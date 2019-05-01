package com.ameba.core.exception;

public class ApiDuplicateReqException extends ApiException {
  public ApiDuplicateReqException() {
    super();
  }

  public ApiDuplicateReqException(Throwable cause) {
    super(cause);
  }

  @Override
  protected ResponseCode defaultResponseCode() {
    return ApiResponseCode.REQ_DUPLICATE;
  }
}
