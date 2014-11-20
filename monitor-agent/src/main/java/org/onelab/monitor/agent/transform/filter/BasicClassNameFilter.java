package org.onelab.monitor.agent.transform.filter;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicClassNameFilter implements ClassNameClassFilter {
    private static final String patten = "\\$.*|.*\\$\\$.*|" +
            "org/onelab/monitor/agent/.*|" +
            "java/.*|javax/.*|sun/.*|com/sun/.*|" +
            "org/xml/sax/.*|org/jcp/xml/.*|org/eclipse/.*";
    @Override
    public boolean check(String className) {
        return !className.matches(patten);
    }
}
