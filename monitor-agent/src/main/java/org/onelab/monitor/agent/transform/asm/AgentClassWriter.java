package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassWriter;
import org.onelab.monitor.agent.config.Commons;

/**
 * Created by chunliangh on 14-11-13.
 */
public class AgentClassWriter extends ClassWriter {

    private ClassLoader classLoader;
    public AgentClassWriter(AgentClassReader classReader, ClassLoader loader) {
        super(classReader, AgentUtil.getClassWriterFlags());
        this.classLoader = loader;
    }

    @Override
    protected String getCommonSuperClass(final String type1, final String type2) {
        Class<?> c, d;
        try {
            c = Class.forName(type1.replace('/', '.'), false, classLoader);
            d = Class.forName(type2.replace('/', '.'), false, classLoader);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        if (c.isAssignableFrom(d)) {
            return type1;
        }
        if (d.isAssignableFrom(c)) {
            return type2;
        }
        if (c.isInterface() || d.isInterface()) {
            return Commons.javaObjectType;
        } else {
            do {
                c = c.getSuperclass();
            } while (!c.isAssignableFrom(d));
            return c.getName().replace('.', '/');
        }
    }
}
