package com.ameba.core.actor;

import com.ameba.core.akka.AmebaActor;
import com.ameba.core.util.KryoCmdMsg;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

import akka.event.Logging;
import akka.event.LoggingAdapter;

@Component("AkkaCmdActorTest")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AkkaCmdActorTest extends AmebaActor {
  public static final AtomicLong COUNT = new AtomicLong(0);

  LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
  public AkkaCmdActorTest() {
    registerCommand(KryoCmdMsg.class, this::match);
  }

  private void match(KryoCmdMsg msg) {
    try {
      Thread.sleep(1);
      System.out.println(msg);
      long size = COUNT.incrementAndGet();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
