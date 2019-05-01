package com.ameba.account.api.type;

import lombok.Getter;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AccountStatus {

  NORMAL("正常"),
  LOCKED("锁定");

  private final String description;

  AccountStatus(String description) {
    this.description = description;
  }

  public static Optional<AccountStatus> instance(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
  }
}
