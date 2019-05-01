package com.ameba.account.api.bean.req;


import com.ameba.core.api.ApiReq;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountReq extends ApiReq {

  @NotBlank(message = "name不能为空")
  @Length(max = 32, message = "name最长为32位")
  private String name;

  @NotBlank(message = "owner不能为空")
  @Length(max = 64, message = "owner最长为64位")
  private String owner;

  @NotBlank(message = "accountScope不能为空")
  @Length(max = 32, message = "accountScope最长为32位")
  private String accountScope;

  @NotBlank(message = "accountType不能为空")
  @Length(max = 32, message = "accountType最长为32位")
  private String accountType;

  @NotBlank(message = "accountStatus不能为空")
  @Length(max = 32, message = "accountStatus最长为32位")
  private String accountStatus;

}
