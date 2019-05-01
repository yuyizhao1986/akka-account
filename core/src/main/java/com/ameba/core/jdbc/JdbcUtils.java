package com.ameba.core.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.function.Supplier;

public class JdbcUtils {

  public static <T> Optional<T> get(Supplier<T> supplier) {
    try {
      T result = supplier.get();
      return Optional.ofNullable(result);
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }
}
