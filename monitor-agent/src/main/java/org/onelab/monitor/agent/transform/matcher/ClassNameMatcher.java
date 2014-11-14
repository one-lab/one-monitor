package org.onelab.monitor.agent.transform.matcher;

import org.onelab.monitor.agent.transform.filter.BasicClassNameFilter;
import org.onelab.monitor.agent.transform.filter.BlackListClassNameFilter;
import org.onelab.monitor.agent.transform.filter.ClassNameClassFilter;
import org.onelab.monitor.agent.transform.filter.WhiteListClassNameFilter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-11-14.
 */
public class ClassNameMatcher {

    private static List<ClassNameClassFilter> classNameFilters ;
    static {
        init();
    }

    public static boolean match(String className){
        boolean canTransform = true;
        for (ClassNameClassFilter classNameFilter:classNameFilters){
            canTransform = classNameFilter.check(className);
            if (!canTransform){
                break;
            }
        }
        return canTransform;
    }

    private static void init(){
        classNameFilters = new LinkedList<ClassNameClassFilter>();
        classNameFilters.add(new WhiteListClassNameFilter());
        classNameFilters.add(new BlackListClassNameFilter());
        classNameFilters.add(new BasicClassNameFilter());
    }
}
