package org.onelab.monitor.agent.transform.asm.filter;

/**
 * Created by chunliangh on 14-11-14.
 */
public interface MethodFilter {
    boolean check(String className, String name);
}
