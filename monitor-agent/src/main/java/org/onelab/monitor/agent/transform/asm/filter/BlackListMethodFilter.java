package org.onelab.monitor.agent.transform.asm.filter;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * Created by chunliangh on 14-11-14.
 */
public class BlackListMethodFilter implements MethodFilter {
    private static final String patten = AgentConfig.getMethodBlackListPatten();
    @Override
    public boolean check(String className, String name) {
        if (patten!=null && (className+"#"+name).matches(patten)){
            return false;
        }
        return true;
    }
}
