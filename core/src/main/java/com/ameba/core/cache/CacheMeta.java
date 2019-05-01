package com.ameba.core.cache;

import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
public class CacheMeta {
  private String name;
  private long duration;
  private TimeUnit unit;
  private long maxSize;
}
