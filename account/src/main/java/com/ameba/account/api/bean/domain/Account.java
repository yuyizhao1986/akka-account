package com.ameba.account.api.bean.domain;


import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountStatus;
import com.ameba.account.api.type.AccountType;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Account {
  private String id;
  private String partnerId;
  private String owner;
  private AccountScope accountScope;
  private AccountType accountType;
  private String requestNo;
  private String name;
  private AccountStatus accountStatus;
  private BigDecimal amount = BigDecimal.ZERO;
  private long version = 0L;
  private LocalDateTime createdTime = LocalDateTime.now();
  private LocalDateTime updatedTime = LocalDateTime.now();

  public String buildId(String randomSeq) {
    id = new StringBuilder()
        .append(accountType.getType())
        .append(accountScope.getType())
        .append(randomSeq).toString();
    return id;
  }

  public static AccountType getAccountType(String accountId) {
    return AccountType.instanceByType(Integer.valueOf(accountId.substring(0, 1)));
  }

}
