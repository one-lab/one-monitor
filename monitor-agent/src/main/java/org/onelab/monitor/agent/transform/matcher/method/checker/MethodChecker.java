package org.onelab.monitor.agent.transform.matcher.method.checker;

/**
 * 方法校验器
 * Created by chunliangh on 14-11-14.
 */
public interface MethodChecker {
    boolean check(String className, String name,String description);
}
