package com.ameba.account.service;

import com.ameba.account.api.bean.converter.VoucherConverter;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.type.AccountingStatus;
import com.ameba.account.api.type.AmountType;
import com.ameba.account.config.AccountConfig;
import com.ameba.account.exception.AccountResponseCode;
import com.ameba.account.integration.AccountingResultClient;
import com.ameba.account.repository.AccountDetailRepository;
import com.ameba.account.repository.AccountRepository;
import com.ameba.account.repository.VoucherRepository;
import com.ameba.core.exception.ApiResponseCode;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;

@Setter
@Service
public class VoucherService {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private VoucherRepository voucherRepository;
  @Autowired
  private AccountDetailRepository detailRepository;
  @Autowired
  @Qualifier(AccountConfig.TRANSACTION_NAME)
  private TransactionTemplate transactionTemplate;
  @Autowired
  private AccountingResultClient resultClient;
  @Autowired
  private VoucherConverter voucherConverter;

  public Voucher create(AccountingReq request) {
    Voucher voucher = voucherConverter.convertReq(request).init();
    voucherRepository.create(voucher);
    return voucher;
  }

  public void accounting(Voucher voucher) {
    if (voucher.getAmountType() == AmountType.P) {
      accounting(voucher, this::plus);
    } else {
      accounting(voucher, this::minus);
    }
  }

  private void accounting(Voucher voucher, Consumer<Voucher> consumer) {
    transactionTemplate.execute(s -> {
      consumer.accept(voucher);
      detailRepository.recordDetail(voucher);
      voucherRepository.updateResult(voucher);
      return true;
    });
    resultClient.notifyResult(voucherConverter.convertRes(voucher));
  }

  private void plus(Voucher voucher) {
    boolean result = accountRepository.plusAmount(voucher);
    if (result) {
      voucher.setStatus(AccountingStatus.SUCCEED);
      voucher.setCode(ApiResponseCode.SUCCEED.getCode());
      voucher.setMessage(ApiResponseCode.SUCCEED.getMessage());
    } else {
      voucher.setStatus(AccountingStatus.FAILED);
      voucher.setCode(AccountResponseCode.ACC_NOT_EXIST.getCode());
      voucher.setMessage(AccountResponseCode.ACC_NOT_EXIST.getMessage());
    }
  }

  private void minus(Voucher voucher) {
    boolean result = accountRepository.minusAmount(voucher);
    if (result) {
      voucher.setStatus(AccountingStatus.SUCCEED);
      voucher.setCode(ApiResponseCode.SUCCEED.getCode());
      voucher.setMessage(ApiResponseCode.SUCCEED.getMessage());
    } else {
      voucher.setStatus(AccountingStatus.FAILED);
      voucher.setCode(AccountResponseCode.ACC_BAL_INSUFFICIENT.getCode());
      voucher.setMessage(AccountResponseCode.ACC_BAL_INSUFFICIENT.getMessage());
    }
  }
}
