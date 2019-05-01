package com.ameba.account.config;


import com.ameba.account.integration.AccountingResultClient;
import com.ameba.core.config.DbConfig;
import com.ameba.core.jdbc.MysqlProperty;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ameba.account.repository.mapper",
    sqlSessionTemplateRef = "accountSqlSessionTemplate")
public class AccountConfig extends DbConfig {
  public static final String TRANSACTION_NAME = "accountTransactionTemplate";
  @Autowired
  private AccountProperty accountProperty;

  @Override
  @Bean(name = "accountDataSource")
  public DataSource dataSource() {
    return super.dataSource();
  }

  @Override
  @Bean(name = "accountTransactionManager")
  public DataSourceTransactionManager transactionManager(
      @Qualifier("accountDataSource") DataSource dataSource) {
    return super.transactionManager(dataSource);
  }

  @Override
  @Bean(name = "accountTransactionTemplate")
  public TransactionTemplate transactionTemplate(
      @Qualifier("accountTransactionManager") DataSourceTransactionManager manager) {
    return super.transactionTemplate(manager);
  }

  @Override
  @Bean(name = "accountSqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(
      @Qualifier("accountDataSource") DataSource dataSource) throws Exception {
    return super.sqlSessionFactory(dataSource);
  }

  @Override
  @Bean(name = "accountSqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("accountSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return super.sqlSessionTemplate(sqlSessionFactory);
  }

  @Override
  public MysqlProperty mysqlProperty() {
    return accountProperty;
  }

}
