package com.ameba.core.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;


import com.ameba.core.akka.message.ActorRegionHolder;
import com.ameba.core.akka.message.ActorRegionProperty;
import com.ameba.core.akka.utils.AkkaUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AkkaRegionStarter implements InitializingBean {
  private final Set<ActorRegionProperty> actors;
  @Autowired
  private ActorSystem actorSystem;
  @Autowired
  private SpringExtension springExtension;
  @Autowired
  protected CommandExtractor extractor;

  public AkkaRegionStarter(Set<ActorRegionProperty> actors,
                           AkkaProperty akkaProperty) {
    this.actors = actors;
    Set<String> regions = actors.stream()
        .map(it -> it.getRegion()).collect(Collectors.toSet());
    ActorRegionHolder.register(regions);
  }

  @Override
  public void afterPropertiesSet() {
    final Cluster cluster = Cluster.get(actorSystem);
    log.info("host={} roles={}", cluster.selfAddress().hostPort(),
        AkkaUtils.joinToString(cluster.getSelfRoles()));
    actors.forEach(item -> startRegion(item));
  }

  private void startRegion(ActorRegionProperty property) {
    ClusterShardingSettings settings = ClusterShardingSettings.create(actorSystem)
        .withRole(property.getRole());
    Props props = springExtension.props(property.getActorClazz());
    ClusterSharding.get(actorSystem).start(property.getRegion(), props, settings, extractor);
  }
}
