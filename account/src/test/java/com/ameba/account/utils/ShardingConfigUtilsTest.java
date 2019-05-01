package com.ameba.account.utils;

import akka.actor.ActorSystem;

import com.ameba.account.AbstractTest;
import com.ameba.core.akka.utils.ShardingConfigUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

@Test
public class ShardingConfigUtilsTest extends AbstractTest {
  @Autowired
  private ActorSystem actorSystem;

  public void test() {
    ShardingConfigUtils.generateShardConfig("account",64);
  }

}
