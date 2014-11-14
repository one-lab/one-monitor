package org.onelab.monitor.agent.transform.filter;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * 类名白名单：类名的白名单必须设置，否则代码将不会被监控
 * Created by chunliangh on 14-11-13.
 */
public class WhiteListClassNameFilter implements ClassNameClassFilter {
    private static final String patten = AgentConfig.getWhiteListPatten();
    @Override
    public boolean check(String className) {
        if (patten!=null && className.matches(patten)){
            return true;
        }
        return false;
    }
}
