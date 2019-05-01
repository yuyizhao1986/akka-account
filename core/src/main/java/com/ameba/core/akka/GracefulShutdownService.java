package com.ameba.core.akka;

import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.cluster.Cluster;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class GracefulShutdownService implements ApplicationListener<ContextClosedEvent> {
  @Autowired
  private ActorSystem actorSystem;

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    log.info("Received context closed event");
    Cluster cluster = Cluster.get(this.actorSystem);
    cluster.leave(cluster.selfAddress());
    Future<Terminated> terminate = actorSystem.terminate();
    while (!terminate.isCompleted()) {
      try {
        TimeUnit.MILLISECONDS.sleep(10);
      } catch (Exception ex) {
        log.error("Stop akka error.", ex);
      }
    }
    log.info("Left akka cluster.");
  }
}
