package org.pizza.common.web.response;

import org.pizza.common.web.model.response.ResultCode;
import org.springframework.http.HttpStatus;

/**
 * @author 高巍
 * @since 2020/10/13 2:49 下午
 */
public enum  CommonCode implements ResultCode {
    SUCCESS(HttpStatus.OK.value(), "操作成功"),
    BUSY(HttpStatus.BAD_REQUEST.value(), "操作过于频繁"),
    BIZ_FAILURE(HttpStatus.BAD_REQUEST.value(), "业务异常"),
    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "请求未授权"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "404 没找到请求"),
    MSG_NOT_READABLE(HttpStatus.BAD_REQUEST.value(), "消息不能读取"),
    METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持当前请求方法"),
    MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "不支持当前媒体类型"),
    REQ_REJECT(HttpStatus.FORBIDDEN.value(), "请求被拒绝"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器异常"),
    PARAM_MISS(HttpStatus.BAD_REQUEST.value(), "缺少必要的请求参数"),
    PARAM_TYPE_ERROR(HttpStatus.BAD_REQUEST.value(), "请求参数类型错误"),
    PARAM_BIND_ERROR(HttpStatus.BAD_REQUEST.value(), "请求参数绑定错误"),
    PARAM_VALID_ERROR(HttpStatus.BAD_REQUEST.value(), "参数校验失败"),
    SIGN_VALID_ERROR(HttpStatus.UNAUTHORIZED.value(), "验签失败"),
    WHITE_IP_VALID_ERROR(HttpStatus.UNAUTHORIZED.value(), "IP白名单验证失败");

    private final int code;
    private final String msg;

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static ResultCode of(int code) {

        CommonCode[] var1 = CommonCode.values();
        int var2 = var1.length;
        for (CommonCode c : var1) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private CommonCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
