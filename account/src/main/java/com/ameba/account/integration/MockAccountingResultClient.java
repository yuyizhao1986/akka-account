package com.ameba.account.integration;

import com.ameba.account.api.bean.res.AccountingRes;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Slf4j
public class MockAccountingResultClient implements AccountingResultClient {
  @Override
  public void notifyResult(AccountingRes accountingRes) {

  }
}
