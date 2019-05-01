package com.ameba.account.api.type;


import com.ameba.core.api.DescriptionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum AmountType implements DescriptionEnum {
  P("加"),
  M("减");
  private final String description;

  public static Optional<AmountType> instance(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
  }
}
