package com.ameba.core.json;

import com.google.common.base.Strings;

import com.ameba.core.exception.ApiException;
import com.ameba.core.utils.DateHelper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Getter
public class JacksonProvider implements JsonProvider {
  public static final ObjectMapper OBJECT_MAPPER;

  static {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    mapper.registerModule(createJavaTime());
    mapper.registerModule(new GuavaModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setDateFormat(new SimpleDateFormat(DateHelper.DATETIME));
    mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

    //long类型输出js会丢失
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    mapper.registerModule(simpleModule);

    OBJECT_MAPPER = mapper;
  }

  private static JavaTimeModule createJavaTime() {
    JavaTimeModule timeModule = new JavaTimeModule();
    timeModule.addDeserializer(LocalDate.class,
        new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateHelper.DATE)));
    timeModule.addDeserializer(LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateHelper.DATETIME)));
    timeModule.addSerializer(LocalDate.class,
        new LocalDateSerializer(DateTimeFormatter.ofPattern(DateHelper.DATE)));
    timeModule.addSerializer(LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateHelper.DATETIME)));
    return timeModule;
  }

  @Override
  public String append(String json, Map<String, String> data) {
    if (data == null) {
      return json;
    }
    if (Strings.isNullOrEmpty(json)) {
      return toJson(data);
    }
    Map<String, String> map = toMap(json);
    map.putAll(data);
    return toJson(map);
  }

  @Override
  public String toJson(Object object) {
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (Exception ex) {
      throw new ApiException(ex);
    }
  }

  @Override
  public byte[] toJsonByte(Object object) {
    try {
      return OBJECT_MAPPER.writeValueAsBytes(object);
    } catch (Exception ex) {
      throw new ApiException(ex);
    }
  }

  @Override
  public <T> T toObject(String json, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(json, clazz);
    } catch (Exception ex) {
      throw new ApiException(ex);
    }
  }

  @Override
  public <T> T toObject(byte[] json, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(json, clazz);
    } catch (Exception ex) {
      throw new ApiException(ex);
    }
  }

  @Override
  public Map<String, String> toMap(String json) {
    return toMap(json, HashMap.class, String.class, String.class);
  }

  @Override
  public <K, V> Map<K, V> toMap(String json, Class<K> keyType, Class<V> valueType) {
    return toMap(json, HashMap.class, keyType, valueType);
  }

  @Override
  public <T extends Map, K, V> Map<K, V> toMap(String json,
                                               Class<T> mapType,
                                               Class<K> keyType,
                                               Class<V> valueType) {
    try {
      MapType javaType = OBJECT_MAPPER.getTypeFactory()
          .constructMapType(mapType, keyType, valueType);
      return OBJECT_MAPPER.readValue(json, javaType);
    } catch (Exception ex) {
      throw new ApiException(ex);
    }
  }

  @Override
  public List<String> toList(String json) {
    return toList(json, String.class);
  }

  @Override
  public <V> List<V> toList(String json, Class<V> clazz) {
    return toCollection(json, ArrayList.class, clazz);
  }

  @Override
  public <T extends Collection, V> T toCollection(String json,
                                                  Class<T> listType,
                                                  Class<V> valueType) {
    try {
      CollectionType type = OBJECT_MAPPER.getTypeFactory()
          .constructCollectionType(listType, valueType);
      return OBJECT_MAPPER.readValue(json, type);
    } catch (Exception ex) {
      throw new ApiException(ex);
    }
  }
}
