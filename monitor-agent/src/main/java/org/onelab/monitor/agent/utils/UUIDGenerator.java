package org.onelab.monitor.agent.utils;

import java.math.BigInteger;
import java.util.UUID;

public class UUIDGenerator {

  public static String randomUUID() {
    return new BigInteger(UUID.randomUUID().toString().replaceAll("-",""),16).toString(32);
  }
}