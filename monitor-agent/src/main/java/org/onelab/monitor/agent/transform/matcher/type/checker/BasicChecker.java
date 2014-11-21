package org.onelab.monitor.agent.transform.matcher.type.checker;

import org.onelab.monitor.agent.config.Commons;

/**
 * 基本类校验器
 * Created by chunliangh on 14-11-13.
 */
public class BasicChecker implements TypeChecker {
    @Override
    public boolean check(String className) {
        return !className.matches(Commons.ILLEGAL_PATTERN);
    }
}
