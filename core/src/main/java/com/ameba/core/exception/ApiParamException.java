package com.ameba.core.exception;

public class ApiParamException extends ApiException {

  public ApiParamException() {
    super();
    enhance(ApiResponseCode.PARAM_ERROR);
  }
}
