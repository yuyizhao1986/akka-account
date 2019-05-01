package com.ameba.account.exception;


import com.ameba.core.exception.ApiException;
import com.ameba.core.exception.ResponseCode;

public class AccountApiException extends ApiException {
  public AccountApiException() {
    super();
  }

  public AccountApiException(Throwable cause) {
    super(cause);
  }

  @Override
  protected ResponseCode defaultResponseCode() {
    return AccountResponseCode.ACCOUNT_ERROR;
  }
}
