package com.ameba.core.akka.message;

import com.google.common.collect.Sets;

import java.util.Set;

public class ActorRegionHolder {
  public static final Set<String> REGIONS = Sets.newHashSet();

  public static void register(Set<String> regions) {
    REGIONS.addAll(regions);
  }

  public static boolean contains(String region) {
    return REGIONS.contains(region);
  }
}
