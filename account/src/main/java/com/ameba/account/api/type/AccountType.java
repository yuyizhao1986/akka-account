package com.ameba.account.api.type;


import com.ameba.core.api.DescriptionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum AccountType implements DescriptionEnum {
  BASIC(1, "基本");
  private final Integer type;
  private final String description;

  public static Optional<AccountType> instance(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
  }

  public static AccountType instanceByType(Integer type) {
    return Arrays.stream(values()).filter(it -> it.type.equals(type)).findFirst().get();
  }
}
