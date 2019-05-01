package com.ameba.account.api.bean.req;


import com.ameba.core.api.ApiReq;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class GetAccountReq extends ApiReq {
  @NotBlank(message = "accountId不能为空")
  private String accountId;
}
