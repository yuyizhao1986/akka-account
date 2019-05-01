package com.ameba.account.repository;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.type.AmountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Test
public class AccountDetailRepositoryTest extends AbstractTest {
  @Autowired
  private AccountDetailRepository repository;

  @Test
  public void recordDetail() {
    Voucher voucher = new Voucher();
    voucher.setId(idWorker.nextId());
    voucher.setPartnerId(PARTNER);
    voucher.setRequestNo(idWorker.nextIdString());
    voucher.setProductCode(PRODUCT);
    voucher.setAccountId(accountA.getId());
    voucher.setAmount(BigDecimal.ONE);
    voucher.setAmountType(AmountType.P);
    voucher.init();
    repository.recordDetail(voucher);
  }
}
