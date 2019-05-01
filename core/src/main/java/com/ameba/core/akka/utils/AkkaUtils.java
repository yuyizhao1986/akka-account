package com.ameba.core.akka.utils;

import java.util.Collection;
import java.util.UUID;

public class AkkaUtils {
  public static final String TRACE_ID = "traceId";
  public static final String EMPTY = "";
  public static final String COMMA = ",";
  public static final String DASH = "-";

  public static String uuid() {
    return UUID.randomUUID().toString();
  }

  public static String uuidWithoutDash() {
    return uuid().replaceAll(DASH, EMPTY);
  }

  public static String joinToString(Collection<String> list) {
    if (list == null) {
      return EMPTY;
    }
    return list.stream().reduce(EMPTY, (left, right) -> left + COMMA + right).substring(1);
  }
}
