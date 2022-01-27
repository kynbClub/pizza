package org.pizza.pay.util;


import org.pizza.pay.api.PayErrorExceptionHandler;
import org.pizza.pay.exception.PayErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogExceptionHandler 日志处理器
 * @author
 */
public class LogExceptionHandler implements PayErrorExceptionHandler {

    protected final Logger LOGGER = LoggerFactory.getLogger(PayErrorExceptionHandler.class);

    @Override
    public void handle(PayErrorException e) {

        LOGGER.error("Error happens", e);

    }

}
