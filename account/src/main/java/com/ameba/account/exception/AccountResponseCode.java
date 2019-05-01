package com.ameba.account.exception;


import com.ameba.core.exception.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountResponseCode implements ResponseCode {
  ACCOUNT_ERROR("600000", "账户系统异常", false),
  ACC_NOT_EXIST("600010", "账户不存在", true),
  ACC_BAL_INSUFFICIENT("600020", "账户余额不足", true),
  ACC_STATUS_LOCKED("600030", "账户被锁定", true);

  private String code;
  private String message;
  private boolean fail;

  public static boolean fail(String code) {
    return Arrays.stream(values()).filter(it -> it.fail)
        .anyMatch(it -> it.getCode().equals(code));
  }
}
