package com.ameba.account.config;

import com.ameba.core.jdbc.MysqlProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Slf4j
@Component
@ConfigurationProperties(prefix = "account")
public class AccountProperty extends MysqlProperty {

}
