package com.ameba.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j
public class IpUtils {
  public static final String LOOPBACK_ADDRESS = "127.0.0.1";

  private IpUtils() {
  }

  public static String ipv4Address() {
    try {
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
      while (networkInterfaces.hasMoreElements()) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();
        Enumeration<InetAddress> ipAddresses = networkInterface.getInetAddresses();
        while (ipAddresses.hasMoreElements()) {
          InetAddress ipAddress = ipAddresses.nextElement();
          String hostAddressName = ipAddress.getHostName();
          if (ipAddress instanceof Inet4Address) {
            return hostAddressName;
          }
        }
      }
      return LOOPBACK_ADDRESS;
    } catch (Exception err) {
      log.warn("Failed to load IP address with error {}.", err);
      return LOOPBACK_ADDRESS;
    }
  }
}
