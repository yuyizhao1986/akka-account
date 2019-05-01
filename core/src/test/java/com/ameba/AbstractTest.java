package com.ameba;


import akka.actor.ActorSystem;

import com.ameba.core.akka.CommandGateway;
import com.ameba.core.akka.SpringExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = {AppTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractTest extends AbstractTestNGSpringContextTests {

  @Autowired
  public ActorSystem actorSystem;

  @Autowired
  public SpringExtension springExtension;

  @Autowired
  public CommandGateway gateway;

  protected void waitResult(long millions){
    try{
      Thread.sleep(millions);
    }catch (Exception ex){
      throw new RuntimeException();
    }
  }
}



