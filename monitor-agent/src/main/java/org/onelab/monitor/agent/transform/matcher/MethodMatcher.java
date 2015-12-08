package org.onelab.monitor.agent.transform.matcher;

import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.transform.pattern.MethodPattern;

/**
 * 方法匹配器
 * 匹配成功的方法可以被加强
 * Created by chunliangh on 14-11-14.
 */
public class MethodMatcher {
    private static MethodPattern methodPattern = Agent.config.getMethodPattern();

    public static boolean match(String className, String name, String description, int access) {
        // 构造子校验
        if (name.matches(Commons.CONSTRUCTOR_PATTERN)) return false;
        // 原生方法校验
        if ((access & Opcodes.ACC_NATIVE) !=0) return false;
        // 抽象方法校验
        if ((access & Opcodes.ACC_ABSTRACT) !=0) return false;
        // 白名单校验
        boolean mi = methodPattern.matchInclude(className,name,description);
        // 黑名单校验
        boolean me = methodPattern.matchExclude(className,name,description);
        return mi && me;
    }
}
