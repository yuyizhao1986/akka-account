package com.ameba.core.exception;

public class ApiRemoteException extends ApiException {

  public ApiRemoteException() {
    super();
  }

  public ApiRemoteException(Throwable cause) {
    super(cause);
  }

  @Override
  protected ResponseCode defaultResponseCode() {
    return ApiResponseCode.REMOTE_ERROR;
  }
}