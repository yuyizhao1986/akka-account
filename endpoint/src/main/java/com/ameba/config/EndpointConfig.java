package com.ameba.config;

import com.ameba.core.config.AppCoreProperty;
import com.ameba.endpoint.AccountEndpoint;
import com.ameba.endpoint.AccountingEndpoint;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api")
public class EndpointConfig extends ResourceConfig {
  @Autowired
  private AppCoreProperty appCoreProperty;

  public EndpointConfig() {
    // register properties
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

    // register features
    register(JacksonContextResolver.class);

    register(AccountEndpoint.class);
    register(AccountingEndpoint.class);
  }

}
