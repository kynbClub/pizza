package org.pizza.common.web.mvc.exception;

import org.pizza.common.web.exception.BizException;
import org.pizza.common.web.mvc.util.WebUtil;
import org.pizza.common.web.response.CommonCode;
import org.pizza.common.web.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author 高巍
 * @since 2020/10/13 2:46 下午
 */
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    public ExceptionAdvice() {
    }

    protected void error(Exception e) {
        StringBuffer url = this.getUrl();
        log.error("请求：{} 异常：{} 信息：{}", url, e.getClass().getName(), e);
    }
    protected void warn(Exception e) {
        StringBuffer url = this.getUrl();
        log.warn("请求：{} 异常：{} 信息：{}", url, e.getClass().getName(), e.getMessage());
    }
    protected StringBuffer getUrl() {
        HttpServletRequest request = WebUtil.getRequest();
        StringBuffer url = new StringBuffer();
        if (request != null) {
            url = request.getRequestURL();
        }
        return url;
    }

    @ExceptionHandler({BizException.class})
    @ResponseBody
    public R bizException(BizException e) {
        this.warn(e);
        return R.fail(e);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public R illegalArgumentException(IllegalArgumentException e) {
        this.error(e);
        return R.fail(CommonCode.PARAM_VALID_ERROR.code(), e.getMessage());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public R noHandlerFoundException(NoHandlerFoundException e) {
        this.warn(e);
        return R.fail(CommonCode.NOT_FOUND.code(), CommonCode.NOT_FOUND.msg());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public R missingServletRequestParameterException(MissingServletRequestParameterException e) {
        this.warn(e);
        return R.fail(CommonCode.PARAM_MISS.code(), CommonCode.PARAM_MISS.msg() + ":" + e.getParameterName());
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    @ResponseBody
    public R missingRequestHeaderException(MissingRequestHeaderException e) {
        this.warn(e);
        return R.fail(CommonCode.PARAM_MISS.code(), "缺少必要的请求头参数:" + e.getHeaderName());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public R httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        this.warn(e);
        return R.fail(CommonCode.METHOD_NOT_SUPPORTED.code(), CommonCode.METHOD_NOT_SUPPORTED.msg());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public R constraintViolationException(ConstraintViolationException e) {
        this.warn(e);
        return R.fail(CommonCode.PARAM_VALID_ERROR.code(), e.getMessage());
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public R validationBodyException(BindException e) {
        StringBuilder errorMsg = this.parseErrorMsg(e.getBindingResult());
        log.warn("请求：{} 异常：{} 信息：{}", this.getUrl(), e.getClass().getName(), errorMsg.toString());
        return R.fail(CommonCode.PARAM_BIND_ERROR.code(), errorMsg.toString());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = this.parseErrorMsg(e.getBindingResult());
        log.warn("请求：{} 异常：{} 信息：{}", this.getUrl(), e.getClass().getName(), errorMsg.toString());
        return R.fail(CommonCode.PARAM_BIND_ERROR.code(), errorMsg.toString());
    }

    protected StringBuilder parseErrorMsg(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append("参数绑定错误:");
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            errors.forEach((p) -> {
                FieldError fieldError = (FieldError) p;
                String field = fieldError.getField();
                errorMsg.append("字段:[")
                        .append(field)
                        .append("],错误信息:[")
                        .append(fieldError.getDefaultMessage())
                        .append("],");
            });
        }
        errorMsg.deleteCharAt(errorMsg.length() - 1);
        return errorMsg;
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public R httpMessageNotReadableException(HttpMessageNotReadableException e) {
        this.error(e);
        return R.fail(CommonCode.PARAM_BIND_ERROR);
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public R npe(NullPointerException e) {
        error(e);
        return R.fail(CommonCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public R exception(Exception e) {
        this.error(e);
        return R.fail(CommonCode.INTERNAL_SERVER_ERROR);
    }
}
