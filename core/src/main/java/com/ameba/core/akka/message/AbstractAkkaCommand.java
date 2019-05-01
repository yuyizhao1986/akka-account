package com.ameba.core.akka.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractAkkaCommand implements AkkaCommand {
  private Long commandId;
}
