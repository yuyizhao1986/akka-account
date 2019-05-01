package com.ameba.core.akka.utils;


public class ShardingConfigUtils {

  private static final String DISPATCHER = "#region#-dispatcher-#index# {\n"
      + "  type = Dispatcher\n"
      + "  executor = \"thread-pool-executor\"\n"
      + "  thread-pool-executor {\n"
      + "    fixed-pool-size = 1\n"
      + "  }\n"
      + "  throughput = 1\n"
      + "}\n"
      + "#region#-mailbox-#index# {\n"
      + "  mailbox-type = \"akka.dispatch.UnboundedMailbox\"\n"
      + "}\n";

  private static final String DEPLOYMENT = "\"/sharding/#region#/#index#/**\" {\n"
      + "    dispatcher = #region#-dispatcher-#index#\n"
      + "    mailbox = #region#-mailbox-#index#\n"
      + "  }\n";

  public static void generateShardConfig(String region, int shardCount) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < shardCount; i++) {
      String text = DISPATCHER.replaceAll("#region#", region);
      text = text.replaceAll("#index#", String.valueOf(i));
      builder.append(text);
    }
    builder.append("akka.actor.deployment {\n");
    for (int i = 0; i < shardCount; i++) {
      String text = DEPLOYMENT.replaceAll("#region#", region);
      text = text.replaceAll("#index#", String.valueOf(i));
      builder.append(text);
    }
    builder.append("}");
    System.out.println(builder.toString());
  }
}
