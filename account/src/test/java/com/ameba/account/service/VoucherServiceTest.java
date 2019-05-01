package com.ameba.account.service;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.type.AmountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Test
public class VoucherServiceTest extends AbstractTest {

  @Autowired
  private VoucherService voucherService;

  @Test
  public void accounting(){
    Voucher voucher =  voucherService.create(create(AmountType.P));
    voucherService.accounting(voucher);
    waitResult(1000);
  }

}
