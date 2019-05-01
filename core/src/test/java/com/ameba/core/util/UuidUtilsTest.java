package com.ameba.core.util;

import com.ameba.AbstractTest;
import com.ameba.core.akka.utils.AkkaUtils;
import com.ameba.core.akka.utils.ShardingConfigUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class UuidUtilsTest extends AbstractTest {

  public void uuid() {
    String id = AkkaUtils.uuid();
    Assert.assertNotNull(id);
  }

  @Test
  public void shardConfig()
  {
    ShardingConfigUtils.generateShardConfig("test",2);
  }

}
