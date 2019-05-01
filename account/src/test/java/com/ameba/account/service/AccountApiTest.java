package com.ameba.account.service;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.AccountApi;
import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.req.GetAccountReq;
import com.ameba.account.api.bean.res.AccountRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AccountApiTest extends AbstractTest {
  @Autowired
  private AccountApi accountApi;

  public void create() {
    AccountReq req = createReq(idWorker.nextIdString());
    AccountRes accountRes = accountApi.create(req);
    Assert.assertNotNull(accountRes);
    GetAccountReq getReq = new GetAccountReq();
    getReq.setPartnerId(PARTNER);
    getReq.setRequestNo(idWorker.nextIdString());
    getReq.setAccountId(accountRes.getId());
    AccountRes res = accountApi.get(getReq);
    Assert.assertNotNull(res);
  }


}
