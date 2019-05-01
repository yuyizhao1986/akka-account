package com.ameba.core.util;

import com.ameba.AbstractTest;
import com.ameba.core.akka.AkkaSerializeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import akka.actor.ActorSystem;

import java.time.LocalDateTime;

@Test
public class AkkaSerializeUtilsTest extends AbstractTest {
  @Autowired
  private ActorSystem actorSystem;

  public void serialize(){
    byte[] bytes = AkkaSerializeUtils.serialize(actorSystem, LocalDateTime.now());
    LocalDateTime time = AkkaSerializeUtils.deserialize(actorSystem, bytes, LocalDateTime.class);
    Assert.assertNotNull(time);

    KryoCmdMsg test = new KryoCmdMsg();
    test.setAggregateId("123");

    bytes = AkkaSerializeUtils.serialize(actorSystem, test);
    KryoCmdMsg cmd = AkkaSerializeUtils.deserialize(actorSystem, bytes, KryoCmdMsg.class);
    Assert.assertTrue(cmd.getAggregateId().equals(test.getAggregateId()));
  }
}
