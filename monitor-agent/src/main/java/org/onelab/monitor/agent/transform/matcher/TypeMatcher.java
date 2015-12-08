package org.onelab.monitor.agent.transform.matcher;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.transform.pattern.TypePattern;

/**
 * 类匹配器
 * 匹配成功的类可以被加强
 * Created by chunliangh on 14-11-14.
 */
public class TypeMatcher {

    private static TypePattern typePattern = Agent.config.getTypePattern();

    public static boolean match(String className){
        // 非法名单匹配
        boolean ml = typePattern.matchIllegal(className);
        if (ml) return false;
        // 强制名单匹配
        boolean mf = typePattern.matchForceInclude(className);
        if (mf) return true;
        // 白名单校验
        boolean mi = typePattern.matchInclude(className);
        // 黑名单校验
        boolean me = typePattern.matchExclude(className);
        return mi && me;
    }
}
