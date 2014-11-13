package org.onelab.monitor.agent.transform.filter;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicFilter implements ClassNameFilter {
    private static final String patten = "\\$.*|.*\\$\\$.*|org/onelab/monitor/agent/.*|java/.*|javax/.*|sun/.*|com/sun/.*";
    @Override
    public boolean check(String className) {
        return !className.matches(patten);
    }
}
