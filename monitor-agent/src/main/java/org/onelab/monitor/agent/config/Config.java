package org.onelab.monitor.agent.config;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chunliangh on 14-12-7.
 */
public class Config {

    public Log log = new Log();
    public Type type = new Type();
    public Method method = new Method();
    public Set<String> codeinserterbuilders = new HashSet<String>();
    public class Log {
        public String level;
        public boolean console;
    }
    public class Type{
        public boolean privateOn;
        public Set<String> includepatterns = new HashSet<String>();
        public Set<String> excludepatterns = new HashSet<String>();
        public Set<String> forceincludepatterns = new HashSet<String>();
    }
    public class Method{
        public boolean privateOn;
        public Set<MethodPattern> includepatterns = new HashSet<MethodPattern>();
        public Set<MethodPattern> excludepatterns = new HashSet<MethodPattern>();
    }
    public static class MethodPattern{
        public String owner;
        public String name;
        public String desc;
    }
}
