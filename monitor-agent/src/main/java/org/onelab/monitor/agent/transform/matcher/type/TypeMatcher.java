package org.onelab.monitor.agent.transform.matcher.type;

import org.onelab.monitor.agent.transform.matcher.type.checker.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 类匹配器
 * 匹配成功的类可以被加强
 * Created by chunliangh on 14-11-14.
 */
public class TypeMatcher {

    private static List<TypeChecker> classNameFilters ;
    private static ForceListChecker forceListFilter;
    static {
        init();
    }

    public static boolean match(String className){
        boolean canTransform = true;
        for (TypeChecker classNameFilter:classNameFilters){
            canTransform = classNameFilter.check(className);
            if (!canTransform){
                break;
            }
        }
        if (!canTransform){
            canTransform = forceListFilter.check(className);
        }
        return canTransform;
    }

    private static void init(){
        forceListFilter = new ForceListChecker();
        classNameFilters = new LinkedList<TypeChecker>();
        classNameFilters.add(new BasicChecker());
        classNameFilters.add(new BlackListChecker());
        classNameFilters.add(new WhiteListChecker());
    }
}
