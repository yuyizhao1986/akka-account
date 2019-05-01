package com.ameba.core.akka;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class AkkaLocalCache implements InitializingBean {
  @Autowired
  private AkkaProperty akkaProperty;
  private Cache<String, Object> cache;

  public <E> void put(String key, E value) {
    cache.put(key, value);
  }

  public <E> Optional<E> get(String key) {
    Object obj = cache.getIfPresent(key);
    return obj == null ? Optional.empty() : Optional.of((E) obj);
  }

  public <E> E getOrPut(String key, Supplier<E> supplier) {
    return (E) cache.get(key, (it) -> supplier.get());
  }

  public void invalidate(String key) {
    cache.invalidate(key);
  }

  public void invalidateAll() {
    cache.invalidateAll();
  }

  @Override
  public void afterPropertiesSet() {
    cache = Caffeine.newBuilder()
        .expireAfterAccess(akkaProperty.getCacheSeconds(), TimeUnit.SECONDS)
        .maximumSize(akkaProperty.getCacheMaxSize())
        .build();
  }
}
