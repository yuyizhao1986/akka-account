package com.ameba.core.config;

import com.ameba.core.cache.LocalCacheManager;
import com.ameba.core.jdbc.MysqlProperty;
import com.ameba.core.utils.IdWorker;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableCaching
public class CoreConfig {
  @Autowired
  private AppCoreProperty property;

  @Bean
  public IdWorker idWorker() {
    return new IdWorker(0L, property.getAppSeq());
  }

  @Bean(LocalCacheManager.NAME)
  @Primary
  public LocalCacheManager memoryCacheManager() {
    return new LocalCacheManager();
  }
}
