package com.ameba.core.akka;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Component
public class AkkaProperty {
  @Value("${akka.shardCount:8}")
  private int shardCount;
  @Value("${akka.command.delayMinutes:1}")
  private int commandDelayMinutes;
  @Value("${akka.command.IntervalMills:500}")
  private int commandIntervalMills;
  @Value("${akka.cache.maxSize:100000}")
  private long cacheMaxSize;
  @Value("${akka.cache.seconds:30}")
  private long cacheSeconds;

  public FiniteDuration commandDelayMinutes() {
    return Duration.create(commandDelayMinutes, TimeUnit.MINUTES);
  }

  public FiniteDuration commandIntervalMills() {
    return Duration.create(commandIntervalMills, TimeUnit.MILLISECONDS);
  }
}
