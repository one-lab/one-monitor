package org.onelab.monitor.agent.transform.matcher;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.config.pattern.TypePattern;

/**
 * 类匹配器
 * 匹配成功的类可以被加强
 * Created by chunliangh on 14-11-14.
 */
public class TypeMatcher {

    private static TypePattern typePattern = Agent.config.getTypePattern();

    public static boolean match(String className){

        if (typePattern.matchIllegal(className)){
            return false;
        }
        boolean canTransform = typePattern.matchInclude(className);
        if (canTransform){
            canTransform = !typePattern.matchExclude(className);
        }
        if (!canTransform){
            canTransform = typePattern.matchForceInclude(className);
        }
        return canTransform;
    }
}
