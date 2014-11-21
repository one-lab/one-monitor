package org.onelab.monitor.agent.transform.matcher.method;

import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.config.AgentConfig;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.transform.matcher.method.checker.BlackListChecker;
import org.onelab.monitor.agent.transform.matcher.method.checker.MethodChecker;
import org.onelab.monitor.agent.transform.matcher.method.checker.WhiteListChecker;

import java.util.LinkedList;
import java.util.List;

/**
 * 方法匹配器
 * 匹配成功的方法可以被加强
 * Created by chunliangh on 14-11-14.
 */
public class MethodMatcher {
    private static boolean canAccPrivateMethod ;
    private static boolean canAccSetMethod ;
    private static boolean hasMethodFilter ;
    private static List<MethodChecker> methodFilters ;
    static {
        init();
    }

    public static boolean match(String className, String name, String description, int access) {

        if (name.matches(Commons.CONSTRUCTOR_PATTERN)) return false;
        if (!canAccPrivateMethod && (access & Opcodes.ACC_PRIVATE)!=0) return false;
        if (!canAccSetMethod && name.startsWith(Commons.SET)) return false;
        if (hasMethodFilter) {
            for (MethodChecker methodFilter:methodFilters){
                if (!methodFilter.check(className,name,description)){
                    return false;
                }
            }
        }
        return true;
    }

    private static void init(){
        canAccPrivateMethod = AgentConfig.getPrivateMethod();
        canAccSetMethod = AgentConfig.getSetMethod();
        hasMethodFilter = AgentConfig.getMethodFilter();
        if (hasMethodFilter){
            methodFilters = new LinkedList<MethodChecker>();
            methodFilters.add(new WhiteListChecker());
            methodFilters.add(new BlackListChecker());
        }
    }
}
