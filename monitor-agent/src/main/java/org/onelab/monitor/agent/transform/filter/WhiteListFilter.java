package org.onelab.monitor.agent.transform.filter;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * Created by chunliangh on 14-11-13.
 */
public class WhiteListFilter implements ClassNameFilter {
    private static final String patten = AgentConfig.getWhiteListPatten();
    @Override
    public boolean check(String className) {
        if (className!=null&&className.matches(patten)){
            return true;
        }
        return false;
    }
}
