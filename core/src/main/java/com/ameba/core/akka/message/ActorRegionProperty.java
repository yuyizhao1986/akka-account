package com.ameba.core.akka.message;


import com.ameba.core.akka.AmebaActor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActorRegionProperty {
  private final Class<? extends AmebaActor> actorClazz;
  private final Class<? extends AkkaCommand> commandClazz;
  private final String region;
  private final String role;

  public ActorRegionProperty(Class<? extends AmebaActor> actorClazz,
                             Class<? extends AkkaCommand> commandClazz,
                             String region) {
    this(actorClazz, commandClazz, region, "");
  }
}
