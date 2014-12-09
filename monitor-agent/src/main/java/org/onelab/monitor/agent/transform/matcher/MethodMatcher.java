package org.onelab.monitor.agent.transform.matcher;

import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.config.pattern.MethodPattern;

/**
 * 方法匹配器
 * 匹配成功的方法可以被加强
 * Created by chunliangh on 14-11-14.
 */
public class MethodMatcher {
    private static MethodPattern methodPattern = Agent.config.getMethodPattern();

    public static boolean match(String className, String name, String description, int access) {

        if (name.matches(Commons.CONSTRUCTOR_PATTERN)) return false;
        if ((access & Opcodes.ACC_NATIVE) !=0) return false;
        if ((access & Opcodes.ACC_ABSTRACT) !=0) return false;
        if (!methodPattern.isPrivateOn() && (access & Opcodes.ACC_PRIVATE)!=0) return false;
        boolean canTransform;
        canTransform = methodPattern.matchInclude(className,name,description);
        if (canTransform){
            canTransform = !methodPattern.matchExclude(className,name,description);
        }
        return canTransform;
    }
}
