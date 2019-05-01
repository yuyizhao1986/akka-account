package com.ameba.core.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    return commonExecutor();
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new SimpleAsyncUncaughtExceptionHandler();
  }

  @Bean("commonAsyncExecutor")
  public Executor commonExecutor() {
    MdcThreadPoolExecutor executor = new MdcThreadPoolExecutor();
    executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
    executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
    executor.setQueueCapacity(3000);
    executor.setThreadGroupName("CommonAsyncExecutor");
    executor.setThreadNamePrefix("CommonAsyncExecutorThread-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
    executor.initialize();
    return executor;
  }

  @Bean("akkaMessageExecutor")
  public Executor akkaMessageExecutor() {
    MdcThreadPoolExecutor executor = new MdcThreadPoolExecutor();
    executor.setCorePoolSize(1);
    executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
    executor.setThreadGroupName("AkkaMessageExecutor");
    executor.setThreadNamePrefix("AkkaMessageExecutorThread-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
    executor.initialize();
    return executor;
  }
}
