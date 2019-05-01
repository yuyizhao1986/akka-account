package com.ameba.account.actor;

import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.service.VoucherService;
import com.ameba.core.akka.AmebaActor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("AccountActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class AccountActor extends AmebaActor {
  @Autowired
  private VoucherService voucherService;

  public AccountActor() {
    registerCommand(Voucher.class, this::match);
  }

  private void match(Voucher voucher) {
    try {
      voucherService.accounting(voucher);
    } catch (Exception ex) {
      log.error("Process accounting error", ex);
    }
  }
}
