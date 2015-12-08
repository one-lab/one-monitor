package org.onelab.monitor.agent.transform.pattern;

import org.onelab.monitor.agent.config.Commons;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 类匹配模板
 * Created by chunliangh on 14-12-8.
 */
public class TypePattern {
    private Set<Pattern> includepatterns;
    private Set<Pattern> excludepatterns;
    private Set<Pattern> forceincludepatterns;
    private Pattern illegalPattern;

    public TypePattern(Set<String> include, Set<String> exclude, Set<String> forceInclude) {
        setInclude(include);
        setExclude(exclude);
        setForceInclude(forceInclude);
        illegalPattern = Pattern.compile(Commons.ILLEGAL_PATTERN);
    }

    private void setInclude(Set<String> include){
        if (include != null){
            for (String var:include){
                if (includepatterns == null){
                    includepatterns = new HashSet<Pattern>();
                }
                if (var!=null){
                    includepatterns.add(Pattern.compile(var));
                }
            }
        }
    }
    private void setExclude(Set<String> exclude){
        excludepatterns = new HashSet<Pattern>();
        if (exclude != null){
            for (String var:exclude){
                if (var!=null){
                    excludepatterns.add(Pattern.compile(var));
                }
            }
        }
        excludepatterns.add(Pattern.compile(Commons.DEFAULT_EXCLUDE));
    }
    private void setForceInclude(Set<String> forceInclude){
        if (forceInclude != null){
            for (String var:forceInclude){
                if (var!=null){
                    if (forceincludepatterns == null){
                        forceincludepatterns = new HashSet<Pattern>();
                    }
                    forceincludepatterns.add(Pattern.compile(var));
                }
            }
        }
    }

    public boolean matchInclude(String type){
        if (includepatterns == null || includepatterns.isEmpty()) return true;
        for (Pattern pattern:includepatterns){
            if (pattern.matcher(type).matches()) return true;
        }
        return false;
    }

    public boolean matchExclude(String type){
        if (excludepatterns == null || excludepatterns.isEmpty()) return true;
        for (Pattern pattern:excludepatterns){
            if (pattern.matcher(type).matches()) return false;
        }
        return true;
    }

    public boolean matchForceInclude(String type){
        if (forceincludepatterns == null || forceincludepatterns.isEmpty()) return false;
        for (Pattern pattern:forceincludepatterns){
            if (pattern.matcher(type).matches()) return true;
        }
        return false;
    }

    public boolean matchIllegal(String type){
        return illegalPattern.matcher(type).matches();
    }
}
