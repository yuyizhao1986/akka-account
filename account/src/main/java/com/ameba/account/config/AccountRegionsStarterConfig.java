package com.ameba.account.config;

import com.google.common.collect.Sets;

import com.ameba.account.actor.AccountActor;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.core.akka.AkkaProperty;
import com.ameba.core.akka.AkkaRegionStarter;
import com.ameba.core.akka.message.ActorRegionProperty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AccountRegionsStarterConfig {
  public static final String REGION = "account";
  @Autowired
  private AkkaProperty akkaProperty;

  @Bean("accountAkkaStarter")
  public AkkaRegionStarter akkaStarter() {
    Set<ActorRegionProperty> actors = Sets.newHashSet();
    actors.add(new ActorRegionProperty(AccountActor.class,
        Voucher.class, REGION, ""));
    return new AkkaRegionStarter(actors, akkaProperty);
  }

  public static String getRegion() {
    return REGION;
  }
}
