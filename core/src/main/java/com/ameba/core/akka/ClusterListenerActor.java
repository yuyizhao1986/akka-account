package com.ameba.core.akka;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ClusterListenerActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
@Setter
public class ClusterListenerActor extends AbstractActor {
  private Cluster cluster = Cluster.get(getContext().system());

  @Override
  public void preStart() {
    cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
        ClusterEvent.MemberEvent.class, UnreachableMember.class);

  }

  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(MemberUp.class, memberUp -> {
          log.info("Member is up: {}", memberUp.member().address());
        })
        .match(UnreachableMember.class, unreachable -> {
          log.info("Member is unreachable: {}", unreachable.member().address());
        })
        .match(MemberRemoved.class, removed -> {
          log.info("Member is removed: {}", removed.member().address());
        })
        .match(ClusterEvent.MemberLeft.class, left -> {
          log.info("Member is left: {}", left.member().address());
        })
        .match(MemberEvent.class, message -> {
        }).build();
  }

}
