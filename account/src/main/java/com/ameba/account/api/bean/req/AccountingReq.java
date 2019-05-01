package com.ameba.account.api.bean.req;


import com.ameba.account.api.type.AmountType;
import com.ameba.core.api.ApiReq;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountingReq extends ApiReq {

  @Length(max = 32, message = "accountId最长为32位")
  @NotBlank(message = "accountId不能为空")
  private String accountId;

  @NotBlank(message = "productCode不能为空")
  @Length(max = 16, message = "productCode最长为16位")
  private String productCode;

  private String description;

  @DecimalMin(value = "0.0001", message = "amount最小金额为0.0001")
  @NotNull(message = "amount不能为空")
  private BigDecimal amount;

  @NotNull(message = "amountType不能为空")
  private AmountType amountType;

}
