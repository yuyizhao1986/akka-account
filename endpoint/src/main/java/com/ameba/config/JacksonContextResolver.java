package com.ameba.config;

import com.ameba.core.json.JacksonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


@Provider
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return JacksonProvider.OBJECT_MAPPER;
  }
}
