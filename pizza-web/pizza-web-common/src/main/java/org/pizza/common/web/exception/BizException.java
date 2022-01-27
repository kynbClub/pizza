package org.pizza.common.web.exception;

import org.pizza.common.web.model.response.ResultCode;
import org.pizza.common.web.response.CommonCode;

/**
 * @author 高巍
 * @since 2020/10/13 2:53 下午
 */
public class BizException extends RuntimeException {
    private final int code;
    private final String message;

    public BizException(ResultCode statusCode) {
        this.code = statusCode.code();
        this.message = statusCode.msg();
    }

    public BizException(ResultCode statusCode, String message) {
        this.code = statusCode.code();
        this.message = statusCode.msg() + ":" + message;
    }

    public BizException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public BizException(String message) {
        this.code = CommonCode.BIZ_FAILURE.getCode();
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

