package org.pizza.common.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.pizza.common.web.exception.BizException;
import org.pizza.common.web.model.response.Response;
import org.pizza.common.web.model.response.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @author 高巍
 * @since  2020/10/13 2:52 下午
 */
@JsonInclude(Include.NON_NULL)
@ApiModel(
        value = "服务响应",
        description = "通用服务响应结果"
)
public class R<T> implements Response<T> {
    @ApiModelProperty(
            value = "是否成功",
            example = "true",
            required = true
    )
    private boolean success;
    @ApiModelProperty(
            value = "状态码，成功:200",
            example = "200",
            required = true
    )
    private int code;
    @ApiModelProperty(
            value = "描述，错误描述",
            example = "SUCCESS",
            required = true
    )
    private String message;
    @ApiModelProperty("数据")
    private T data;

    public R() {
    }

    private R(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private R(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public static <T> R<T> ok() {
        return new R(true, CommonCode.SUCCESS.code(), CommonCode.SUCCESS.msg());
    }

    public static <T> R<T> ok(T data) {
        return new R(true, CommonCode.SUCCESS.code(), CommonCode.SUCCESS.msg(), data);
    }

    public static <T> R<T> ok(String message, T data) {
        return new R(true, CommonCode.SUCCESS.code(), message, data);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return new R(false, resultCode.code(), resultCode.msg());
    }

    public static <T> R<T> fail(ResultCode resultCode, String message) {
        return new R(false, resultCode.code(), message);
    }

    public static <T> R<T> fail(BizException e) {
        return new R(false, e.getCode(), e.getMessage());
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R(false, code, msg);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "R(success=" + this.isSuccess() + ", code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
