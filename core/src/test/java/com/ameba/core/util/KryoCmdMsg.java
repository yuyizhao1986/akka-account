package com.ameba.core.util;


import com.ameba.core.akka.message.AbstractAkkaCommand;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KryoCmdMsg extends AbstractAkkaCommand {
  private String aggregateId;
}
