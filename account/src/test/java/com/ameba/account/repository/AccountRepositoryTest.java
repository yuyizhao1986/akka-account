package com.ameba.account.repository;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountStatus;
import com.ameba.account.api.type.AccountType;
import com.ameba.core.exception.ApiDuplicateReqException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

@Test
public class AccountRepositoryTest extends AbstractTest {

  @Autowired
  private AccountRepository repository;

  @Test
  public void createAndGet() {
    Account account = new Account();
    account.setRequestNo(idWorker.nextIdString());
    account.setName("name");
    account.setPartnerId(PARTNER);
    account.setOwner(idWorker.nextIdString());
    account.setAccountStatus(AccountStatus.NORMAL);
    account.setAccountType(AccountType.BASIC);
    account.setAccountScope(AccountScope.CORPORATE);
    Assert.assertEquals(repository.create(account), 1);
    Optional<Account> optional = repository.get(account.getId());
    Assert.assertTrue(optional.isPresent());

    Optional<Account> acc = repository.select(PARTNER, account.getOwner(),
        account.getAccountScope(), account.getAccountType());
    Assert.assertTrue(acc.isPresent());
    try {
      repository.create(account);
    } catch (Exception ex) {
      Assert.assertTrue(ex instanceof ApiDuplicateReqException);
    }
  }
}
