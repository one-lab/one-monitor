package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.transform.matcher.TypeMatcher;

/**
 * monitor-agent类载入器
 * Created by chunliangh on 14-11-13.
 */
public class AgentClassReader extends ClassReader {

    public AgentClassReader(byte[] b) {
        super(b);
    }

    /**
     * 判断类写入器是否可以处理对应的类
     * @return
     */
    public boolean usable() {
        int access = getAccess();
        // 如果是接口则不予处理
        if ((access & Opcodes.ACC_INTERFACE) != 0) return false;
        // 如果是枚举则不予处理
        if ((access & Opcodes.ACC_ENUM) != 0) return false;
        // 如果是注解则不予处理
        if ((access & Opcodes.ACC_ANNOTATION) != 0) return false;

        return matchType() || matchInterface();
    }

    private boolean matchType(){
        if (TypeMatcher.match(getClassName())) {
            return true;
        }
        return false;
    }

    private boolean matchInterface(){
        // 接口的实现类予以通过
        String[] interfaces = getInterfaces();
        if (interfaces != null){
            for (String inf : interfaces){
                if (TypeMatcher.match(inf)) return true;
            }
        }
        return false;
    }
}
