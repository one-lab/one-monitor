package org.onelab.monitor.agent.transform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记被加强的方法
 * Created by chunliangh on 14-11-19.
 */
@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransformedMethod {
}
