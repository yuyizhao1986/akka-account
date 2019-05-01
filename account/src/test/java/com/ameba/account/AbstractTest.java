package com.ameba.account;

import com.ameba.AccountAppTest;
import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountStatus;
import com.ameba.account.api.type.AccountType;
import com.ameba.account.api.type.AmountType;
import com.ameba.account.repository.AccountRepository;
import com.ameba.core.exception.ApiDuplicateReqException;
import com.ameba.core.utils.IdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = {AccountAppTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractTest extends AbstractTestNGSpringContextTests {
  public static final String PARTNER = "10000000";
  public static final String PRODUCT = "10000";
  public static final String OWNER_A = "owner_a";
  public static final String OWNER_B = "owner_b";
  public static final BigDecimal BILLION = new BigDecimal("1000000000");

  @Autowired
  protected AccountRepository accountRepository;
  @Autowired
  protected IdWorker idWorker;

  protected Account accountA;
  protected Account accountB;

  @BeforeMethod
  public void initAccount() {
    idWorker.setWorkerId(0L);
    accountA = createAccount(OWNER_A);
    accountB = createAccount(OWNER_B);
  }

  private Account createAccount(String owner) {
    Account account = new Account();
    try {
      account.setOwner(owner);
      account.setName(owner);
      account.setPartnerId(PARTNER);
      account.setAccountType(AccountType.BASIC);
      account.setAccountStatus(AccountStatus.NORMAL);
      account.setAccountScope(AccountScope.PRIVATE);
      account.setAmount(BILLION);
      account.setRequestNo(idWorker.nextIdString());
      accountRepository.create(account);
      return account;
    }catch (ApiDuplicateReqException ex){

      Optional<Account> select = accountRepository.select(PARTNER, account.getOwner(), account.getAccountScope(), account.getAccountType());

      return select.get();
    }
  }

  protected AccountReq createReq(String owner){
    AccountReq req = new AccountReq();
    req.setName(owner);
    req.setOwner(owner);
    req.setAccountScope(AccountScope.PRIVATE.name());
    req.setAccountType(AccountType.BASIC.name());
    req.setAccountStatus(AccountStatus.NORMAL.name());
    req.setRequestNo(idWorker.nextIdString());
    req.setPartnerId(PARTNER);
    return req;
  }

  protected AccountingReq create(AmountType type){
    AccountingReq req = new AccountingReq();
    req.setRequestNo(idWorker.nextIdString());
    req.setAccountId(accountA.getId());
    req.setProductCode(PRODUCT);
    req.setAccountId(accountA.getId());
    req.setAmountType(type);
    req.setAmount(BigDecimal.ONE);
    req.setPartnerId(PARTNER);
    return req;
  }

  protected void waitResult(long millions) {
    try {
      Thread.sleep(millions);
    } catch (Exception ex) {
      throw new RuntimeException();
    }
  }

}
