package com.ameba.account.api.bean.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDetail {
  private String id;
  private String accountId;
  private BigDecimal amount;
  private BigDecimal accountingAmount;
  private long voucherId;
  private LocalDateTime createdTime;
}
