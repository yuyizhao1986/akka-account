package com.ameba.core.api;

import com.ameba.core.utils.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ApiRes {
  public static final String SUCCEED = "0";
  private String partnerId;
  private String requestNo;
  private Map<String, String> extension;
  private String code = StringHelper.EMPTY;
  private String message = StringHelper.EMPTY;

  public boolean hasError() {
    return ApiRes.hasError(code);
  }

  public static boolean hasError(String code) {
    return !Objects.equals(code, SUCCEED);
  }
}

