package org.pizza.common.web.aspect;

import javassist.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.pizza.common.web.model.BaseLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author 高巍
 * @since  2020-04-07 3:33 下午
 */
public abstract class ConsoleLogAspect extends BaseLogAspect {
    private final Logger log = LoggerFactory.getLogger(BaseLogAspect.class);

    @Override
    protected BaseLog before(JoinPoint joinPoint) throws NotFoundException {
        BaseLog before = super.before(joinPoint);
        log.info(beforeFormat(before));
        return before;
    }

    @Override
    protected BaseLog after(Object returnVal, BaseLog before) {
        BaseLog after = super.after(returnVal, before);
        log.info(afterFormat(returnVal, after));
        return after;
    }

    protected abstract String beforeFormat(BaseLog baseLog);

    protected abstract String afterFormat(Object returnVal, BaseLog baseLog);

    protected int outThreshold() {
        return 1000;
    }
}
