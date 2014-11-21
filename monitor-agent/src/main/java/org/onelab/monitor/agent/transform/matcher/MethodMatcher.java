package org.onelab.monitor.agent.transform.matcher;

import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.config.AgentConfig;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.transform.asm.filter.BlackListMethodFilter;
import org.onelab.monitor.agent.transform.asm.filter.MethodFilter;
import org.onelab.monitor.agent.transform.asm.filter.WhiteListMethodFilter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-11-14.
 */
public class MethodMatcher {
    private static boolean canAccPrivateMethod ;
    private static boolean canAccSetMethod ;
    private static boolean hasMethodFilter ;
    private static List<MethodFilter> methodFilters ;
    static {
        init();
    }

    public static boolean match(String className, String name, String description, int access) {

        if (name.matches(Commons.CONSTRUCTOR_PATTERN)) return false;
        if (!canAccPrivateMethod && (access & Opcodes.ACC_PRIVATE)!=0) return false;
        if (!canAccSetMethod && name.startsWith(Commons.SET)) return false;
        if (hasMethodFilter) {
            for (MethodFilter methodFilter:methodFilters){
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
            methodFilters = new LinkedList<MethodFilter>();
            methodFilters.add(new WhiteListMethodFilter());
            methodFilters.add(new BlackListMethodFilter());
        }
    }
}
