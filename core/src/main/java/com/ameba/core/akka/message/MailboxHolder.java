package com.ameba.core.akka.message;

import com.google.common.collect.Sets;

import java.util.Set;

public class MailboxHolder {
  public static final Set<String> MAILBOX = Sets.newHashSet();

  public static void register(Set<String> regions) {
    MAILBOX.addAll(regions);
  }

  public static boolean contains(String region) {
    return MAILBOX.contains(region);
  }
}
