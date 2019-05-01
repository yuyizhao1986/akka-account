package com.ameba.account.repository;

import com.ameba.AccountAppTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.sql.DataSource;

@SpringBootTest(classes = {AccountAppTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountSqlOutTest extends AbstractTestNGSpringContextTests {

  @Autowired
  @Qualifier("accountDataSource")
  private DataSource dataSource;
  protected MybatisMapperGenerator generator(){
    return new MybatisMapperGenerator(dataSource,
        "akka_account","com.ameba.account.api.type");

  }

  @Test
  public void account() {
    generator().generateMapper("account");
  }

  @Test
  public void voucher() {
    generator().generateFull("voucher");
  }


}
