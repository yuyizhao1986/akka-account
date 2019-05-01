package com.ameba.core.cache;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import com.ameba.core.exception.Checker;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class LocalCacheManager implements CacheManager {
  public static final String NAME = "localCacheManager";
  private final ConcurrentMap<String, Cache> cacheMap
      = Maps.newConcurrentMap();
  private final Caffeine<Object, Object> defaultBuilder = Caffeine.newBuilder();

  @Override
  public Cache getCache(String name) {
    Cache cache = this.cacheMap.get(name);
    if (cache == null) {
      synchronized (this.cacheMap) {
        cache = this.cacheMap.get(name);
        if (cache == null) {
          cache = createCache(name, defaultBuilder);
        }
      }
    }
    return cache;
  }

  @Override
  public Collection<String> getCacheNames() {
    return Collections.unmodifiableSet(this.cacheMap.keySet());
  }

  /**
   * 创建特殊的cache.
   */
  public void initCaches(List<CacheMeta> metas) {
    metas.forEach(meta -> {
      Checker.checkArgument(!Strings.isNullOrEmpty(meta.getName()),"缓存名称不能为空");
      Caffeine<Object, Object> builder = Caffeine.newBuilder();
      if (meta.getUnit() != null && meta.getDuration() > 0) {
        builder.expireAfterAccess(meta.getDuration(), meta.getUnit());
      }
      if (meta.getMaxSize() > 0) {
        builder.maximumSize(meta.getMaxSize());
      }
      createCache(meta.getName(), builder);
    });
  }

  protected Cache createCache(String name, Caffeine<Object, Object> builder) {
    CaffeineCache cache = new CaffeineCache(name, builder.build(), true);
    this.cacheMap.put(name, cache);
    return cache;
  }

}
