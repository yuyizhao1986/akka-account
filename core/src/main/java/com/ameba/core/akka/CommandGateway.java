package com.ameba.core.akka;

import com.google.common.base.Strings;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.sharding.ClusterSharding;

import com.ameba.core.akka.message.ActorRegionHolder;
import com.ameba.core.akka.message.AkkaCommand;
import com.ameba.core.akka.utils.AmebaAkkaException;
import com.ameba.core.exception.Checker;
import com.ameba.core.utils.IdWorker;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Setter
@Service
@Slf4j
public class CommandGateway {
  @Autowired
  private ActorSystem actorSystem;
  @Autowired
  private IdWorker idWorker;

  public void send(AkkaCommand command, String region) {
    check(command, region);
    command.setCommandId(idWorker.nextId());
    fire(command, region);
  }

  public <R> Optional<R> sendAndWait(AkkaCommand command,
                                     String region,
                                     long intervalMills,
                                     int queryCount,
                                     Supplier<Optional<R>> supplier) {
    send(command, region);
    return query(intervalMills, queryCount, supplier);
  }

  private <R> Optional<R> query(long intervalMills,
                                int queryCount,
                                Supplier<Optional<R>> supplier) {
    for (int index = 0; index < queryCount; index++) {
      try {
        TimeUnit.MILLISECONDS.sleep(intervalMills);
        Optional<R> result = supplier.get();
        if (result.isPresent()) {
          return result;
        }
      } catch (InterruptedException ex) {
        throw new AmebaAkkaException(ex);
      }
    }
    return Optional.empty();
  }

  private void check(AkkaCommand command, String region) {
    Checker.checkArgument(command != null,
        "Command can not null");
    Checker.checkArgument(!Strings.isNullOrEmpty(region),
        "Region can not blank");
    Checker.checkArgument(ActorRegionHolder.contains(region),
        "Region not exist in the config " + region);
  }

  private void fire(AkkaCommand command, String region) {
    ActorRef actorRef = ClusterSharding.get(actorSystem).shardRegion(region);
    actorRef.tell(command, ActorRef.noSender());
  }

}
