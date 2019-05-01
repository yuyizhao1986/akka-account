package com.ameba.account.api.type;

import lombok.Getter;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AccountScope {
  PRIVATE(1, "对私"),
  CORPORATE(2, "对公");

  private final Integer type;
  private final String description;

  AccountScope(Integer type, String description) {
    this.type = type;
    this.description = description;
  }

  public static Optional<AccountScope> instance(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
  }

  public static AccountScope instanceByType(Integer type) {
    return Arrays.stream(values()).filter(it -> it.type.equals(type)).findFirst().get();
  }
}
