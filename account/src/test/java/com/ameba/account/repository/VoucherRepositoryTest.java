package com.ameba.account.repository;

import com.ameba.account.AbstractTest;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.type.AmountType;
import com.ameba.account.repository.mapper.VoucherSqlMapper;
import com.ameba.core.utils.IdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

@Test
public class VoucherRepositoryTest extends AbstractTest {

  @Autowired
  private VoucherRepository repository;
  @Autowired
  private IdWorker idWorker;
  @Autowired
  private VoucherSqlMapper voucherMapper;

  @Test
  public void createAndGet() {
    Voucher voucher = new Voucher();
    voucher.setPartnerId(PARTNER);
    voucher.setRequestNo(idWorker.nextIdString());
    voucher.setProductCode(PRODUCT);
    voucher.setAccountId(accountA.getId());
    voucher.setAmount(BigDecimal.ONE);
    voucher.setAmountType(AmountType.P);
    voucher.init();
    int rows = repository.create(voucher);
    Assert.assertEquals(rows, 1);

    Optional<Voucher> optional = repository.get(voucher.getId());
    Assert.assertNotNull(optional.isPresent());

    optional = repository.getByRequestNo(voucher.getRequestNo());
    Assert.assertNotNull(optional.isPresent());

  }
}
