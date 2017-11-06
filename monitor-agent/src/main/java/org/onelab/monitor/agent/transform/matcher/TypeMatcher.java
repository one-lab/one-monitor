package org.onelab.monitor.agent.transform.matcher;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.transform.pattern.TypePattern;

/**
 * 类匹配器 匹配成功的类可以被加强 Created by chunliangh on 14-11-14.
 */
public class TypeMatcher {

  private static TypePattern typePattern = Agent.config.getTypePattern();

  /**
   * 匹配给定类名的类
   *
   * @return true 匹配成功, false 匹配失败
   */
  public static boolean match(String className) {
    // 1.非法名单匹配
    if (typePattern.matchIllegal(className)) return false;
    // 2.白名单校验:校验失败返回false
    if (!typePattern.matchInclude(className)) return false;
    // 3.黑名单校验:校验成功返回false
    if (typePattern.matchExclude(className)) return false;

    return true;
  }
}
