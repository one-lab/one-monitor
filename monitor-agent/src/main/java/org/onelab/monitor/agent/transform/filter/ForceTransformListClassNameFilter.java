package org.onelab.monitor.agent.transform.filter;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * Created by chunliangh on 14-11-21.
 */
public class ForceTransformListClassNameFilter implements ClassNameFilter {
    private static final String patten = AgentConfig.getForceListPatten();
    @Override
    public boolean check(String className) {
        if (patten!=null && className.matches(patten)){
            return true;
        }
        return false;
    }
}
