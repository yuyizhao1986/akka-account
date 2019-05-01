package com.ameba.core.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class LocalCacheConfig implements InitializingBean {

  @Autowired
  private LocalCacheManager cacheManager;

  public abstract List<CacheMeta> cacheMetas();

  @Override
  public void afterPropertiesSet() {
    cacheManager.initCaches(cacheMetas());
  }
}
