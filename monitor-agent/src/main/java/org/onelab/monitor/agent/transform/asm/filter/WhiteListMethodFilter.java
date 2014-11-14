package org.onelab.monitor.agent.transform.asm.filter;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * 方法名白名单：如果没有白名单，则认为所有方法是可监控的
 * Created by chunliangh on 14-11-14.
 */
public class WhiteListMethodFilter implements MethodFilter {
    private static final String patten = AgentConfig.getMethodWhiteListPatten();
    @Override
    public boolean check(String className, String name) {
        if (patten!=null){
            if ((className+"#"+name).matches(patten)){
                return true;
            } else return false;
        }
        return true;
    }
}
