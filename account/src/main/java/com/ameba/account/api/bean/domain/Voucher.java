package com.ameba.account.api.bean.domain;

import com.ameba.account.api.type.AccountingStatus;
import com.ameba.account.api.type.AmountType;
import com.ameba.core.akka.message.AkkaCommand;
import com.ameba.core.utils.StringHelper;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Voucher implements AkkaCommand {
  private String accountId;
  private Long commandId;
  private Long id;
  private String partnerId;
  private String productCode;
  private String requestNo;
  private LocalDate accountingDate;

  private BigDecimal amount;
  private AmountType amountType;
  private AccountingStatus status;
  private String code;
  private String message;
  private long version;
  private String description;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;

  public Voucher init() {
    version = 0L;
    if (description == null) {
      description = StringHelper.EMPTY;
    }
    status = AccountingStatus.CREATED;
    code = StringHelper.EMPTY;
    message = StringHelper.EMPTY;
    createdTime = LocalDateTime.now();
    updatedTime = LocalDateTime.now();
    accountingDate = LocalDate.now();
    return this;
  }


  @Override
  public String getAggregateId() {
    return accountId;
  }

}
