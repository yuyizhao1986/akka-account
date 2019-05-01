package com.ameba.core.utils;

import java.util.UUID;

public abstract class IdUtils {
  public static String uuid() {
    return UUID.randomUUID().toString();
  }

  public static String uuidWithoutDash() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
