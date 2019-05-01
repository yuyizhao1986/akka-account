package com.ameba.core.exception;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import lombok.Setter;

@Setter
public class ApiException extends RuntimeException {
  protected ResponseCode responseCode;
  protected String apiMessage;

  public ApiException() {
    super();
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public final String getMessage() {
    String message = super.getMessage();
    return Strings.isNullOrEmpty(message) ? getApiMessage() : message;
  }

  public final String getApiMessage() {
    return Strings.isNullOrEmpty(apiMessage) ? getResponseCode().getMessage() : apiMessage;
  }

  public final ResponseCode getResponseCode() {
    if (responseCode != null) {
      return responseCode;
    }
    if (defaultResponseCode() != null) {
      return defaultResponseCode();
    }
    return ApiResponseCode.EXCEPTION;
  }

  protected ResponseCode defaultResponseCode() {
    return ApiResponseCode.EXCEPTION;
  }

  public final ApiException enhance(ResponseCode responseCode) {
    Preconditions.checkArgument(responseCode != null);
    this.responseCode = responseCode;
    return this;
  }

  public final ApiException enhance(String apiMessage) {
    this.apiMessage = apiMessage;
    return this;
  }

  public final ApiException enhance(ResponseCode responseCode,
                                    String apiMessage) {
    this.apiMessage = apiMessage;
    this.responseCode = responseCode;
    return this;
  }
}
