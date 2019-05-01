package com.ameba.core.akka.utils;

import lombok.Setter;

@Setter
public class AmebaAkkaException extends RuntimeException {

  public AmebaAkkaException(Throwable cause) {
    super(cause);
  }

  public AmebaAkkaException(String message) {
    super(message);
  }

  public AmebaAkkaException(String message, Throwable cause) {
    super(message, cause);
  }
}
