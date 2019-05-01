package com.ameba.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Slf4j
@Component
public class AppCoreProperty {
  @Value("${spring.profile.active:prod}")
  private String profile;
  @Value("${server.port:8000}")
  private int serverPort;
  @Value("${server.minThreads:8}")
  private int serverMinThreads;
  @Value("${server.maxThreads:80}")
  private int serverMaxThreads;

  @Value("${app.id:0}")
  private int appSeq;
  @Value("${app.name:ameba}")
  private String appName;

}
