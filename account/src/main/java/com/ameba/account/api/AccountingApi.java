package com.ameba.account.api;


import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.req.GetAccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;

public interface AccountingApi {
  AccountingRes execute(AccountingReq request);

  AccountingRes get(GetAccountingReq request);
}
