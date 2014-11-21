package org.onelab.monitor.agent.transform.filter;

import org.onelab.monitor.agent.config.Commons;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicClassNameFilter implements ClassNameFilter {
    @Override
    public boolean check(String className) {
        return !className.matches(Commons.ILLEGAL_PATTERN);
    }
}
