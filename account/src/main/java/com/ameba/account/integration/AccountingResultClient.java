package com.ameba.account.integration;

import com.ameba.account.api.bean.res.AccountingRes;

public interface AccountingResultClient {
  void notifyResult(AccountingRes accountingRes);
}
