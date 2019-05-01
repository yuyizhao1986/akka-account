package com.ameba.core.exception;


import com.ameba.core.api.ApiRes;

import lombok.Getter;

@Getter
public enum ApiResponseCode implements ResponseCode {
  SUCCEED(ApiRes.SUCCEED, "处理成功"),
  PARAM_ERROR("100010", "参数错误"),
  REQ_DUPLICATE("100020", "请求重复"),
  DB_ERROR("100030", "存储错误"),
  REMOTE_ERROR("100040", "远程错误"),
  EXCEPTION("999999", "处理失败");

  ApiResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  private String code;
  private String message;
}
