package com.ameba.core;

import com.google.common.collect.Sets;

import com.ameba.core.actor.AkkaCmdActorTest;
import com.ameba.core.akka.AkkaProperty;
import com.ameba.core.akka.AkkaRegionStarter;
import com.ameba.core.akka.message.ActorRegionProperty;
import com.ameba.core.akka.message.AkkaCommandMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AkkaRegionsStarterConfigurationTest {
  @Autowired
  private AkkaProperty akkaProperty;

  @Bean("testAkkaStarter")
  public AkkaRegionStarter akkaStarter() {
    Set<ActorRegionProperty> actors = Sets.newHashSet();
    actors.add(new ActorRegionProperty(AkkaCmdActorTest.class,
        AkkaCommandMsg.class, "test", ""));
    return new AkkaRegionStarter(actors, akkaProperty);
  }
}
