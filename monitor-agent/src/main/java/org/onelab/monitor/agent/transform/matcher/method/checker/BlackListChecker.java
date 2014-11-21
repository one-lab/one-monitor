package org.onelab.monitor.agent.transform.matcher.method.checker;

import org.onelab.monitor.agent.config.AgentConfig;

/**
 * 方法黑名单校验器
 * Created by chunliangh on 14-11-14.
 */
public class BlackListChecker implements MethodChecker {
    private static final String patten = AgentConfig.getMethodBlackListPatten();
    @Override
    public boolean check(String className, String name,String description) {
        if (patten!=null && (className+"#"+name+"#"+description).matches(patten)){
            return false;
        }
        return true;
    }
}
