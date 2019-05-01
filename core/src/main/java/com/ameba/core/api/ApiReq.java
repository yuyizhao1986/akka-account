package com.ameba.core.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.Length;

import java.util.Map;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString(exclude = {"extension"})
public class ApiReq {

  @NotBlank(message = "partnerId不能为空")
  @Length(min = 8, max = 8, message = "partnerId长度为8位")
  private String partnerId;

  @NotBlank(message = "requestNo不能为空")
  @Length(max = 64, message = "requestNo最长为64位")
  private String requestNo;

  private Map<String, String> extension;
}
