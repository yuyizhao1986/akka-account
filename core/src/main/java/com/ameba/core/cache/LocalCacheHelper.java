package com.ameba.core.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class LocalCacheHelper {
  @Autowired
  @Qualifier(LocalCacheManager.NAME)
  private CacheManager cacheManager;

  protected Cache getCache(String name) {
    return cacheManager.getCache(name);
  }

  @SuppressWarnings("unchecked")
  public <E> Optional<E> get(String name, String key, Class<E> type) {
    Object obj = getCache(name).get(key, type);
    return obj == null ? Optional.empty() : Optional.of((E) obj);
  }

  public Optional<String> get(String name, String key) {
    String obj = getCache(name).get(key, String.class);
    return obj == null ? Optional.empty() : Optional.of(obj);
  }

  public Collection<String> getCacheNames() {
    return cacheManager.getCacheNames();
  }


  public <E> void put(String name, String key, E value) {
    getCache(name).put(key, value);
  }


  @SuppressWarnings("unchecked")
  public <E> E getOrPut(String name, String key, Supplier<E> supplier) {
    Cache.ValueWrapper wrapper = getCache(name).get(key);
    if (wrapper == null) {
      put(name, key, supplier.get());
    }
    wrapper = getCache(name).get(key);
    return (E) wrapper.get();
  }

  public void invalidate(String name,
                         String key) {
    getCache(name).evict(key);
  }

  public void invalidate(String name) {
    getCache(name).clear();
  }

  public void invalidate() {
    cacheManager.getCacheNames().forEach(it -> invalidate(it));
  }
}
