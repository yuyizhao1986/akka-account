package com.ameba.account.service;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.AccountingApi;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.req.GetAccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;
import com.ameba.account.api.type.AmountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AccountingApiTest extends AbstractTest {
  @Autowired
  private AccountingApi accountingApi;

  @Test
  public void execute() {
    AccountingReq req = create(AmountType.P);
    accountingApi.execute(req);

    waitResult(1000);
    GetAccountingReq getReq = new GetAccountingReq();
    getReq.setRequestNo(req.getRequestNo());
    getReq.setPartnerId(PARTNER);
    AccountingRes res = accountingApi.get(getReq);
    Assert.assertNotNull(res);
  }

  @Test
  public void test(){
    for(int i=0;i<10000;i++){
      AccountingReq req = create(AmountType.P);
      accountingApi.execute(req);
    }
    waitResult(10000);
  }
}
