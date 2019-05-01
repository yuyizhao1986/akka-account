package com.ameba.account.api.type;

import lombok.Getter;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AccountingStatus {
  CREATED("创建"),
  SUCCEED("成功"),
  FAILED("失败");

  private final String description;

  AccountingStatus(String description) {
    this.description = description;
  }

  public static Optional<AccountingStatus> instance(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
  }

  public boolean isFinal() {
    return this == SUCCEED || this == FAILED;
  }
}
