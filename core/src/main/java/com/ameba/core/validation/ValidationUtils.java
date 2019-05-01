package com.ameba.core.validation;

import com.google.common.base.Strings;

import com.ameba.core.exception.ApiParamException;
import com.ameba.core.utils.CollectionHelper;
import com.ameba.core.utils.StringHelper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public abstract class ValidationUtils {

  private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

  public static List<String> validateToList(Object param) {
    return FACTORY.getValidator().validate(param).stream()
        .map(it -> it.getMessage()).collect(Collectors.toList());
  }

  public static String validateToString(Object param) {
    List<String> errors = validateToList(param);
    if (errors.isEmpty()) {
      return StringHelper.EMPTY;
    }
    Collections.sort(errors);
    return CollectionHelper.joinToString(errors, StringHelper.COMMA);
  }

  public static void validateToApiException(Object param) {
    String error = validateToString(param);
    if (Strings.isNullOrEmpty(error)) {
      return;
    }
    throw new ApiParamException().enhance(error);
  }
}
