package org.pizza.common.web.exception;

import org.pizza.common.web.model.response.ResultCode;
import org.pizza.common.web.response.CommonCode;

/**
 * @author 高巍
 * @since 2020/10/13 2:53 下午
 */
public enum Errors {
    SYSTEM(CommonCode.INTERNAL_SERVER_ERROR.code(), CommonCode.INTERNAL_SERVER_ERROR.msg()),
    PARAM(CommonCode.PARAM_VALID_ERROR.code(), CommonCode.PARAM_VALID_ERROR.msg()),
    BIZ(CommonCode.BIZ_FAILURE.code(), CommonCode.BIZ_FAILURE.msg());

    private int code;
    private String message;

    public BizException exception(ResultCode code) {
        return new BizException(code.code(), code.msg());
    }

    public BizException exception(String msg) {
        return new BizException(this.code, msg);
    }

    public BizException exception(ResultCode code, String msg) {
        return new BizException(code, msg);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
