package org.onelab.monitor.agent.transform.matcher;

import org.onelab.monitor.agent.transform.filter.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-11-14.
 */
public class ClassNameMatcher {

    private static List<ClassNameFilter> classNameFilters ;
    private static ForceTransformListClassNameFilter forceListFilter;
    static {
        init();
    }

    public static boolean match(String className){
        boolean canTransform = true;
        for (ClassNameFilter classNameFilter:classNameFilters){
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
        forceListFilter = new ForceTransformListClassNameFilter();
        classNameFilters = new LinkedList<ClassNameFilter>();
        classNameFilters.add(new BasicClassNameFilter());
        classNameFilters.add(new BlackListClassNameFilter());
        classNameFilters.add(new WhiteListClassNameFilter());
    }
}
