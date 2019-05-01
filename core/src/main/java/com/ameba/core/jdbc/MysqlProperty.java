package com.ameba.core.jdbc;

import com.google.common.collect.Maps;

import com.zaxxer.hikari.HikariConfig;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Setter
@Getter
public class MysqlProperty {
  private String dsDriverClass;
  private int dsMaxActive;
  private String dsUrl;
  private String dsUserName;
  private String dsPassword;

  private Map<String, String> propertyMap = Maps.newHashMap();

  public MysqlProperty() {
    propertyMap.put("cachePrepStmts", "true");
    propertyMap.put("prepStmtCacheSize", "250");
    propertyMap.put("prepStmtCacheSqlLimit", "2048");
    propertyMap.put("useServerPrepStmts", "true");
    propertyMap.put("useLocalSessionState", "true");
    propertyMap.put("useLocalTransactionState", "true");
    propertyMap.put("rewriteBatchedStatements", "true");
    propertyMap.put("cacheResultSetMetadata", "true");
    propertyMap.put("cacheServerConfiguration", "true");
    propertyMap.put("elideSetAutoCommits", "true");
    propertyMap.put("maintainTimeStats", "true");
  }

  public HikariConfig toHikariConfig() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(this.getDsDriverClass());
    config.setJdbcUrl(this.getDsUrl());
    config.setUsername(this.getDsUserName());
    config.setPassword(this.getDsPassword());
    this.getPropertyMap().forEach((key, value) -> {
      config.addDataSourceProperty(key, value);
    });
    return config;
  }
}
