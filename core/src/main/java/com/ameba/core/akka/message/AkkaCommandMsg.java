package com.ameba.core.akka.message;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AkkaCommandMsg extends AbstractAkkaCommand {
  private String aggregateId;
}
