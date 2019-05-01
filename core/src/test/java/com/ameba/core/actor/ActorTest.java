package com.ameba.core.actor;

import com.ameba.AbstractTest;
import com.ameba.core.akka.CommandGateway;
import com.ameba.core.util.KryoCmdMsg;

import org.awaitility.Awaitility;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;


@Test
public class ActorTest extends AbstractTest {
  @Autowired
  private CommandGateway commandGateway;

  public void send()  {
    long millis = System.currentTimeMillis();
    for (int i = 1; i <= 10; i++){
      KryoCmdMsg msg = new KryoCmdMsg();
      msg.setAggregateId(String.valueOf(1));
      commandGateway.send(msg, "test");
    }

    long end = System.currentTimeMillis();
    Awaitility.await().forever().until(() -> {
      return AkkaCmdActorTest.COUNT.longValue() >= 10;
    });

    System.out.println("end******" + (end - millis));
  }

}
