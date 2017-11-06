package org.onelab.monitor.agent.transform.pattern;

import org.onelab.monitor.agent.config.Const;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 类匹配模板
 * Created by chunliangh on 14-12-8.
 */
public class TypePattern {
    private Set<Pattern> includepatterns = new HashSet<Pattern>();
    private Set<Pattern> excludepatterns = new HashSet<Pattern>();
    private Pattern illegalPattern ;

    public TypePattern(Set<String> include, Set<String> exclude) {
        setInclude(include);
        setExclude(exclude);
        illegalPattern = Pattern.compile(Const.ILLEGAL_CLASS_PATTERN);
    }

    private void setInclude(Set<String> include){
        if (include != null){
            for (String var : include){
                if (var!=null){
                    includepatterns.add(Pattern.compile(var.trim()));
                }
            }
        }
    }
    private void setExclude(Set<String> exclude){
        if (exclude != null){
            for (String var : exclude){
                if (var!=null){
                    excludepatterns.add(Pattern.compile(var.trim()));
                }
            }
        }
    }

    public boolean matchInclude(String type){
        for (Pattern pattern : includepatterns){
            if (pattern.matcher(type).matches()) return true;
        }
        return false;
    }

    public boolean matchExclude(String type){
        for (Pattern pattern : excludepatterns){
            if (pattern.matcher(type).matches()) return true;
        }
        return false;
    }

    public boolean matchIllegal(String type){
        return illegalPattern.matcher(type).matches();
    }
}
