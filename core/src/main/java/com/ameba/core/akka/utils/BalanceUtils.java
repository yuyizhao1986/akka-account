package com.ameba.core.akka.utils;

public abstract class BalanceUtils {
  public static int hash(String balanceKey, int maxInstances) {
    int hash = balanceKey.hashCode();
    if (hash < 0) {
      hash = Math.abs(hash + 1);
    }
    return hash % maxInstances;
  }
}
