package org.onelab.monitor.agent.transform.asm.inserter.builder;

import org.onelab.monitor.agent.transform.asm.inserter.CodeInserter;

/**
 * 代码插入器生成器
 * Created by chunliangh on 14-12-5.
 */
public interface CodeInserterBuilder {
    /**
     * 生成指定的代码插入器
     * @return
     */
    CodeInserter build();
}
