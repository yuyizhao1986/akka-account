package com.ameba.core.akka;

import com.google.common.collect.Maps;

import akka.actor.AbstractActor;

import com.ameba.core.akka.message.AkkaCommand;
import com.ameba.core.akka.utils.AkkaUtils;
import com.ameba.core.akka.utils.AmebaAkkaException;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Setter
@Slf4j
public abstract class AmebaActor extends AbstractActor {
  @Autowired
  protected AkkaProperty coreProperty;
  @Autowired
  protected CommandGateway commandGateway;
  @Autowired
  protected AkkaLocalCache localCache;

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(AkkaCommand.class, this::match)
        .build();
  }

  private void match(AkkaCommand command) {
    try {
      MDC.put(AkkaUtils.TRACE_ID, command.getAggregateId());
      log.info("Received {} {}", command.getClass().getSimpleName(), this.getSelf().path());
      commandMap.get(command.getClass().getName()).accept(command);
    } catch (Exception ex) {
      throw new AmebaAkkaException("Process command error", ex);
    } finally {
      MDC.remove(AkkaUtils.TRACE_ID);
    }
  }

  private Map<String, Consumer<AkkaCommand>> commandMap = Maps.newHashMap();

  protected <E extends AkkaCommand> void registerCommand(Class<E> cmdClass,
                                                         Consumer<E> consumer) {
    commandMap.put(cmdClass.getName(), (Consumer<AkkaCommand>) consumer);
  }

  protected <T> T aggregation(String aggregationId, Supplier<T> supplier) {
    if (cacheAggregation()) {
      return localCache.getOrPut(cacheKey(aggregationId), supplier);
    }
    return supplier.get();
  }

  protected void clearCache(String aggregationId) {
    if (cacheAggregation()) {
      localCache.invalidate(cacheKey(aggregationId));
    }
  }

  protected boolean cacheAggregation() {
    return false;
  }

  protected String aggregationPrefix() {
    return "Aggregation";
  }

  protected String cacheKey(String aggregationId) {
    return String.format("%s_%s", aggregationPrefix(), aggregationId);
  }
}
