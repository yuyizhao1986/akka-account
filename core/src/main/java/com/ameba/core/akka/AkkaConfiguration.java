package com.ameba.core.akka;

import akka.actor.ActorSystem;

import com.ameba.core.config.AppCoreProperty;
import com.typesafe.config.ConfigFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

@Configuration
public class AkkaConfiguration {
  @Autowired
  private AppCoreProperty coreProperty;
  @Autowired
  private AkkaProperty akkaProperty;

  @Bean
  @Qualifier("akkaForkJoinPool")
  public ForkJoinPool forkJoinPool() {
    return new ForkJoinPool(Runtime.getRuntime().availableProcessors());
  }

  @Bean
  public ActorSystem actorSystem() {
    return ActorSystem.create(coreProperty.getAppName(), ConfigFactory.load());
  }

}
