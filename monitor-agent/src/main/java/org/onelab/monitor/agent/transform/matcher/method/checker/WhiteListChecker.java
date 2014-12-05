package org.onelab.monitor.agent.transform.matcher.method.checker;

import org.onelab.monitor.agent.Agent;

/**
 * 方法白名单校验器
 * Created by chunliangh on 14-11-14.
 */
public class WhiteListChecker implements MethodChecker {
    private static final String patten = Agent.config.getMethodWhiteListPatten();
    @Override
    public boolean check(String className, String name,String description) {
        if (patten!=null){
            if ((className+"#"+name+"#"+description).matches(patten)){
                return true;
            } else return false;
        }
        return true;
    }
}
