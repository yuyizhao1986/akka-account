package com.ameba.core.akka;

import akka.actor.ActorSystem;
import akka.serialization.Serialization;
import akka.serialization.SerializationExtension;
import akka.serialization.Serializer;

public class AkkaSerializeUtils {

  public static byte[] serialize(ActorSystem actorSystem, Object object) {
    Serialization serialization = SerializationExtension.get(actorSystem);
    Serializer serializer = serialization.findSerializerFor(object);
    return serializer.toBinary(object);
  }

  public static <T> T deserialize(ActorSystem actorSystem, byte[] bytes, Class<T> clazz) {
    Serialization serialization = SerializationExtension.get(actorSystem);
    return serialization.deserialize(bytes, clazz).get();
  }
}
