package org.onelab.monitor.agent.transform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记被加强的类
 * Created by chunliangh on 14-11-14.
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransformedClass {
}
