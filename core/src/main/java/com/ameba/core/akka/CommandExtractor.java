package com.ameba.core.akka;

import akka.cluster.sharding.ShardRegion;


import com.ameba.core.akka.message.AkkaCommand;
import com.ameba.core.akka.utils.BalanceUtils;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Component
public class CommandExtractor implements ShardRegion.MessageExtractor {
  @Autowired
  private AkkaProperty akkaProperty;

  @Override
  public String entityId(Object message) {
    if (message instanceof AkkaCommand) {
      return ((AkkaCommand) message).getAggregateId();
    }
    return null;
  }

  @Override
  public Object entityMessage(Object message) {
    if (message instanceof AkkaCommand) {
      return message;
    }
    return null;
  }

  @Override
  public String shardId(Object message) {
    if (message instanceof AkkaCommand) {
      return shard(((AkkaCommand) message).getAggregateId());
    }
    return null;
  }

  private String shard(String shardId) {
    return String.valueOf(BalanceUtils.hash(shardId, akkaProperty.getShardCount()));
  }
}
