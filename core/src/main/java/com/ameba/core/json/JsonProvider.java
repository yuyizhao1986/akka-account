package com.ameba.core.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface JsonProvider {
  JacksonProvider INSTANCE = new JacksonProvider();

  String append(String json, Map<String, String> data);

  String toJson(Object object);

  byte[] toJsonByte(Object object);

  <T> T toObject(String json, Class<T> clazz);

  <T> T toObject(byte[] json, Class<T> clazz);

  Map<String, String> toMap(String json);

  <K, V> Map<K, V> toMap(String json,
                         Class<K> keyType,
                         Class<V> valueType);

  <T extends Map, K, V> Map<K, V> toMap(String json,
                                        Class<T> mapType,
                                        Class<K> keyType,
                                        Class<V> valueType);

  List<String> toList(String json);

  <V> List<V> toList(String json, Class<V> clazz);

  <T extends Collection, V> T toCollection(String json, Class<T> listType, Class<V> valueType);
}
