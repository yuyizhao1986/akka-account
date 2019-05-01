package com.ameba.core.akka.message;

public interface AkkaCommand {
  Long getCommandId();

  void setCommandId(Long commandId);

  String getAggregateId();
}
