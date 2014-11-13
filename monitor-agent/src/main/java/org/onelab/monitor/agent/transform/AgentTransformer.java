package org.onelab.monitor.agent.transform;

import org.onelab.monitor.agent.transform.asm.BasicTransformer;
import org.onelab.monitor.agent.transform.filter.BasicFilter;
import org.onelab.monitor.agent.transform.filter.BlackListFilter;
import org.onelab.monitor.agent.transform.filter.ClassNameFilter;
import org.onelab.monitor.agent.transform.filter.WhiteListFilter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-11-13.
 */
public class AgentTransformer implements ClassFileTransformer {
    List<ClassNameFilter> classNameFilters = new LinkedList<ClassNameFilter>();
    public AgentTransformer(){
        init();
    }
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        boolean canTransform = true;
        for (ClassNameFilter classNameFilter:classNameFilters){
            canTransform = classNameFilter.check(className);
            if (!canTransform){
                break;
            }
        }
        if (canTransform) {
            System.out.println("-------------------------------------------");
            System.out.println("==className:"+className);
            System.out.println("-------------------------------------------");
            return BasicTransformer.transform(loader, className, classfileBuffer);
        }
        return null;
    }
    private void init(){
        classNameFilters.add(new WhiteListFilter());
        classNameFilters.add(new BlackListFilter());
        classNameFilters.add(new BasicFilter());
    }
}
