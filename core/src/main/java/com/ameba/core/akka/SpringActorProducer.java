package com.ameba.core.akka;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;

import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

  private final ApplicationContext context;
  private final String beanName;
  private final Object[] args;

  public SpringActorProducer(ApplicationContext context,
                             String beanName,
                             Object... args) {
    this.context = context;
    this.beanName = beanName;
    this.args = args;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Actor produce() {
    if (args == null) {
      return context.getBean(beanName, Actor.class);
    }
    return (Actor) context.getBean(beanName, args);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<? extends Actor> actorClass() {
    return (Class<? extends Actor>) context.getType(beanName);
  }

}
