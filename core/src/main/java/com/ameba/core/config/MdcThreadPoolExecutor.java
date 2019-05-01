package com.ameba.core.config;

import com.google.common.base.Strings;

import com.ameba.core.utils.IdUtils;
import com.ameba.core.utils.StringHelper;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class MdcThreadPoolExecutor extends ThreadPoolTaskExecutor {
  private static final long serialVersionUID = 1L;

  @Override
  protected ExecutorService initializeExecutor(
      ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
    super.setTaskDecorator(new MdcContextTaskDecorator());
    return super.initializeExecutor(threadFactory, rejectedExecutionHandler);
  }

  class MdcContextTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
      String traceId = MDC.get(StringHelper.TRACE_ID);
      if (Strings.isNullOrEmpty(traceId)) {
        traceId = IdUtils.uuidWithoutDash();
      }
      return new MdcContextTask(runnable, traceId);
    }
  }

  static class MdcContextTask implements Runnable {
    private final Runnable task;
    private final String traceId;

    MdcContextTask(Runnable task, String traceId) {
      this.task = task;
      this.traceId = traceId;
    }

    public void run() {
      try {
        MDC.put(StringHelper.TRACE_ID, traceId);
        task.run();
      } catch (Exception ex) {
        log.error("Async task run error", ex);
      } finally {
        MDC.clear();
      }
    }
  }
}
