package com.ameba.account.api;

import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.req.GetAccountReq;
import com.ameba.account.api.bean.res.AccountRes;

public interface AccountApi {
  AccountRes create(AccountReq accountRequest);

  AccountRes get(GetAccountReq accountGetRequest);
}
