package com.ameba.account.api;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.req.GetAccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;
import com.ameba.account.api.type.AccountingStatus;
import com.ameba.account.api.type.AmountType;
import com.ameba.core.api.ApiRes;
import com.ameba.core.exception.ApiResponseCode;

import org.awaitility.Awaitility;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Test
public class AccountingApiTest extends AbstractTest {
  @Autowired
  private AccountingApi accountingApi;

  @Test
  public void accounting() throws InterruptedException {
    AccountingReq req = new AccountingReq();
    req.setRequestNo(idWorker.nextIdString());
    req.setAccountId(accountA.getId());
    req.setProductCode(PRODUCT);
    req.setAmount(BigDecimal.ONE);
    req.setAmountType(AmountType.P);
    req.setPartnerId(PARTNER);
    AccountingRes accountingRes = accountingApi.execute(req);
    Assert.assertTrue(accountingRes.getCode().equals(ApiResponseCode.SUCCEED.getCode()));
    Thread.sleep(10000);
    Awaitility.await().forever().until(() -> {
      GetAccountingReq getAccountingReq = new GetAccountingReq();
      getAccountingReq.setRequestNo(req.getRequestNo());
      getAccountingReq.setPartnerId(PARTNER);
      AccountingRes res = accountingApi.get(getAccountingReq);
      return res.getStatus() == AccountingStatus.SUCCEED;
    });
  }
}
