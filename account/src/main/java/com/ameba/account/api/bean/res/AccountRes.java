package com.ameba.account.api.bean.res;



import com.ameba.core.api.ApiRes;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRes extends ApiRes {
  private String id;
  private String name;
  private String owner;
  private String accountScope;
  private String accountType;
  private String accountStatus;
  private BigDecimal amount = BigDecimal.ZERO;
}
