package com.ameba.account.api.bean.res;

import com.ameba.account.api.type.AccountingStatus;
import com.ameba.core.api.ApiRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountingRes extends ApiRes {
  private String voucherId;
  private AccountingStatus status;
  private String accountingDate;
}
