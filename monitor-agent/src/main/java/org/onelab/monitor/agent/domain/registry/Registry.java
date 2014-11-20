package org.onelab.monitor.agent.domain.registry;

/**
 * Created by chunliangh on 14-11-20.
 */
public interface Registry {
    void add(String className,String methodName,String methodDesc);
    void remove(String className,String methodName,String methodDesc);
    void clear();
}
