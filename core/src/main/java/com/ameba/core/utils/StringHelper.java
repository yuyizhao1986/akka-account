package com.ameba.core.utils;

public abstract class StringHelper {
  public static final String EMPTY = "";
  public static final String STAR = "*";
  public static final String COLON = ":";
  public static final String NAMESPACE = "::";
  public static final String EQUAL_SIGN = "=";
  public static final String AMPERSAND = "&";
  public static final String AT = "@";
  public static final String FORWARD_SLASH = "/";
  public static final String DOUBLE_SLASH = "//";
  public static final String SINGLE_QUOTE = "'";
  public static final String COMMA = ",";
  public static final String BLANK = " ";
  public static final String DASH = "-";
  public static final String VERTICAL = "|";
  public static final String UNDERSCORE = "_";
  public static final String DOT = ".";
  public static final String EMPTY_JSON = "{}";
  public static final String TRACE_ID = "traceId";
  public static final String AKKA_THREAD = "sourceThread";

  public static String left(String text, int len) {
    if (text == null) {
      return EMPTY;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (text.length() <= len) {
      return text;
    }
    return text.substring(0, len);
  }

  public static String exceptionMsg(Exception ex, int len) {
    if (ex == null || ex.getMessage() == null) {
      return EMPTY;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (ex.getMessage().length() <= len) {
      return ex.getMessage();
    }
    return ex.getMessage().substring(0, len);
  }

  public static String nullToEmpty(String text) {
    return text == null ? EMPTY : text;
  }
}
