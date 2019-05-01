package com.ameba.core.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CollectionHelper {

  public static <T> Collection<String> mapCollection(Collection<T> list) {
    return list.stream().map(it -> it.toString()).collect(Collectors.toList());
  }

  public static <T> String joinCollection(Collection<T> list) {
    return joinToString(mapCollection(list));
  }

  public static String joinToString(Collection<String> list) {
    if (list == null) {
      return StringHelper.EMPTY;
    }
    return list.stream().reduce(StringHelper.EMPTY, (left, right) -> left + StringHelper.COMMA + right).substring(1);
  }

  public static String joinToString(Collection<String> list, String separator) {
    if (list == null) {
      return StringHelper.EMPTY;
    }
    return list.stream().reduce(StringHelper.EMPTY, (left, right) -> left + separator + right).substring(1);
  }


  public static List<String> splitToList(String text) {
    if (Strings.isNullOrEmpty(text)) {
      return Lists.newArrayList();
    }
    return Arrays.stream(text.split(StringHelper.COMMA)).collect(Collectors.toList());
  }
}
