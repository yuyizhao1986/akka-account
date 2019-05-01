package com.ameba.account.exception;


import com.ameba.core.exception.ResponseCode;

public class AccountNotExistedException extends AccountApiException {
  public AccountNotExistedException() {
    super();
  }

  public AccountNotExistedException(Throwable cause) {
    super(cause);
  }

  @Override
  protected ResponseCode defaultResponseCode() {
    return AccountResponseCode.ACC_NOT_EXIST;
  }
}
