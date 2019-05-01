package com.ameba.core.utils;

import lombok.Setter;

public class IdWorker {
  private static final long EPOCH = 1288834974657L;
  private static final long DATA_CENTER_ID_BITS = 2L;
  private static final long WORKER_ID_BITS = 8L;
  private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
  private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
  private static final long SEQUENCE_BITS = 12L;
  private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
  private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
  private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
  private static final long TIMESTAMP_LEFT_SHIFT =
      SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

  private long dataCenterId;
  @Setter
  private long workerId;
  private long sequence = 0L;
  private long lastTimestamp = -1L;

  /**
   * Generates unique ID numbers.
   */
  public IdWorker(long dataCenterId, long workerId) {
    if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
      throw new IllegalArgumentException(String.format(
          "Data Center ID can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
    }

    if (workerId > MAX_WORKER_ID || workerId < 0) {
      throw new IllegalArgumentException(String.format(
          "Worker ID can't be greater than %d or less than 0", MAX_WORKER_ID));
    }

    this.dataCenterId = dataCenterId;
    this.workerId = workerId;
  }

  /**
   * Gets next ID as string.
   */
  public String nextIdString() {
    return String.valueOf(nextId());
  }

  /**
   * Gets next ID.
   */
  public synchronized long nextId() {
    long timestamp = timeGen();
    if (timestamp < lastTimestamp) {
      throw new IllegalStateException(
          String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & SEQUENCE_MASK;
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;
    }

    lastTimestamp = timestamp;

    return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
        | (dataCenterId << DATA_CENTER_ID_SHIFT)
        | (workerId << WORKER_ID_SHIFT)
        | sequence;
  }

  private long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  private long timeGen() {
    return System.currentTimeMillis();
  }
}
