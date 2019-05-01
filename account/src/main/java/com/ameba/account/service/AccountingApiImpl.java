package com.ameba.account.service;

import com.ameba.account.api.AccountingApi;
import com.ameba.account.api.bean.converter.VoucherConverter;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.req.GetAccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;
import com.ameba.account.api.type.AccountingStatus;
import com.ameba.account.config.AccountRegionsStarterConfig;
import com.ameba.account.exception.AccountResponseCode;
import com.ameba.account.repository.AccountRepository;
import com.ameba.account.repository.VoucherRepository;
import com.ameba.core.akka.CommandGateway;
import com.ameba.core.api.ApiTemplate;
import com.ameba.core.exception.Checker;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
@Slf4j
public class AccountingApiImpl implements AccountingApi {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private VoucherRepository voucherRepository;
  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private VoucherConverter voucherConverter;
  @Autowired
  private VoucherService voucherService;

  @Override
  public AccountingRes execute(AccountingReq request) {
    return new ApiTemplate<AccountingReq, AccountingRes>(new AccountingRes()) {
      @Override
      protected AccountingRes process(AccountingReq request) {
        Voucher voucher = voucherService.create(request);
        commandGateway.send(voucher, AccountRegionsStarterConfig.getRegion());
        return voucherConverter.convertRes(voucher);
      }

      @Override
      protected AccountingRes enrichResponse(AccountingRes response) {
        AccountingRes accountingRes = super.enrichResponse(response);
        accountingRes.setRequestNo(request.getRequestNo());
        if (AccountResponseCode.fail(accountingRes.getCode())) {
          accountingRes.setStatus(AccountingStatus.FAILED);
        }
        return accountingRes;
      }
    }.execute(request, "accounting", log);
  }

  @Override
  public AccountingRes get(GetAccountingReq request) {
    return new ApiTemplate<GetAccountingReq, AccountingRes>(new AccountingRes()) {
      @Override
      protected AccountingRes process(GetAccountingReq request) {
        Optional<Voucher> voucher = voucherRepository.getByRequestNo(request.getRequestNo());
        Checker.checkArgument(voucher.isPresent(), String.format("交易%s不存在", request.getRequestNo()));
        return voucherConverter.convertRes(voucher.get());
      }
    }.execute(request, "get-accounting", log);
  }
}
