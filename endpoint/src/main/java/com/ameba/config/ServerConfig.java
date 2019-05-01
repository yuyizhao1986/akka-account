package com.ameba.config;

import com.ameba.core.config.AppCoreProperty;

import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
  @Autowired
  private AppCoreProperty coreProperty;

  @Bean
  public JettyServletWebServerFactory containerFactory() {
    JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
    factory.addServerCustomizers(new JettyServerCustomizer() {
      @Override
      public void customize(Server server) {
        QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
        threadPool.setName("JettyThread");
        threadPool.setMinThreads(coreProperty.getServerMinThreads());
        threadPool.setMaxThreads(coreProperty.getServerMaxThreads());
        threadPool.setStopTimeout(5000);
        threadPool.setIdleTimeout(60000);
        LowResourceMonitor lowResourceMonitor = new LowResourceMonitor(server);
        lowResourceMonitor.setMaxLowResourcesTime(300);
        server.addBean(lowResourceMonitor);
      }
    });
    return factory;
  }

}
