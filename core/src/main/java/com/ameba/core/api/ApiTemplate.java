package com.ameba.core.api;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import com.ameba.core.akka.utils.AkkaUtils;
import com.ameba.core.exception.ApiException;
import com.ameba.core.exception.ApiResponseCode;
import com.ameba.core.validation.ValidationUtils;

import org.slf4j.Logger;
import org.slf4j.MDC;

public abstract class ApiTemplate<T extends ApiReq, R extends ApiRes> {

  private final R defaultResponse;
  private final boolean validate;

  public ApiTemplate(R response) {
    Preconditions.checkNotNull(response);
    this.defaultResponse = response;
    this.validate = true;
  }

  public ApiTemplate(R response, boolean validate) {
    Preconditions.checkNotNull(response);
    this.defaultResponse = response;
    this.validate = validate;
  }

  public R execute(T request, String apiName, Logger logger) {
    try {
      MDC.put(AkkaUtils.TRACE_ID, request.getRequestNo());
      logger.info("[api={},req={}]", apiName, request);
      if (validate) {
        ValidationUtils.validateToApiException(request);
      }
      validate(request);
      R response = process(request);
      if (Strings.isNullOrEmpty(response.getCode())) {
        response.setCode(ApiResponseCode.SUCCEED.getCode());
        response.setMessage(ApiResponseCode.SUCCEED.getMessage());
      }
      logger.info("[api={},res={}]", apiName, response);
      return response;
    } catch (ApiException ex) {
      if (ex.getCause() == null) {
        logger.error("[api={} error={}]", apiName, ex.getMessage());
      } else {
        logger.error(String.format("[api=%s]", apiName), ex);
      }
      return enrichResponse(instance(ex));
    } catch (Throwable ex) {
      logger.error(String.format("[api=%s]", apiName), ex);
      return enrichResponse(instance(new ApiException()));
    } finally {
      MDC.remove(AkkaUtils.TRACE_ID);
    }
  }


  protected R instance(ApiException exception) {
    defaultResponse.setCode(exception.getResponseCode().getCode());
    defaultResponse.setMessage(exception.getApiMessage());
    return defaultResponse;
  }

  protected void validate(T request) {
  }

  protected abstract R process(T request);

  protected R enrichResponse(R response) {
    return response;
  }
}
