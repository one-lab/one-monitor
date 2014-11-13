package org.onelab.monitor.agent.transform.filter;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BlackListFilter implements ClassNameFilter {
    private static final String patten = AgentConfig.getBlackListPatten();
    @Override
    public boolean check(String className) {
        if (patten!=null && className.matches(patten)){
            return false;
        }
        return true;
    }
}
