package com.ameba.core.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class DateHelper {
  public static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DateHelper.DATETIME);
  public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DateHelper.DATE);
  public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE = "yyyy-MM-dd";


  public static DateTimeFormatter of(String format) {
    return DateTimeFormatter.ofPattern(format);
  }

  public static String currentDatetime() {
    return LocalDateTime.now().format(DateHelper.DATETIME_FMT);
  }

  public static String currentDate() {
    return LocalDateTime.now().format(DateHelper.DATE_FMT);
  }

  public static LocalDateTime toLocalTime(Date date) {
    if (date == null) {
      return null;
    }
    Instant instant = date.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    return LocalDateTime.ofInstant(instant, zone);
  }
}
