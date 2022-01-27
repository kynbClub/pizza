package org.pizza.common.web.mvc.aspect;

import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.pizza.common.web.aspect.ConsoleLogAspect;
import org.pizza.common.web.model.BaseLog;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 高巍
 * @since 2020/10/13 2:44 下午
 */
public abstract class WebConsoleLogAspect extends ConsoleLogAspect {
    public WebConsoleLogAspect() {
    }

    protected String beforeFormat(BaseLog before) {
        JSONObject jsonObject = JSONUtil.parseObj(before.getMethodParam());
        jsonObject.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        return null != before.getMethodParam() ?
                String.format("================== web console log aspect start  ===================\n1.当前类为:%s , 调用的方法开始：%s \n2.当前类参数列表：%s", before.getMethodClass(), before.getMethodName(), prettyPrint() ? jsonObject.toStringPretty() : jsonObject.toString())
                :
                String.format("================== web console log aspect start  ===================\n1.当前类为：%s,调用的方法开始：%s \n2.当前方法没有参数", before.getMethodClass(), before.getMethodName());
    }

    protected String afterFormat(Object returnVal, BaseLog after) {
        String returnValueJson = "";
        if (null != returnVal) {
            if (after.getReturnValue().length() <= super.outThreshold()) {
                returnValueJson = prettyPrint() ? JSONUtil.formatJsonStr(after.getReturnValue()) : after.getReturnValue();
            } else {
                returnValueJson = "******(压缩)";
            }
        }

        long seconds = after.getExecTime() / 1000L;
        String warn;
        if (seconds > 3L) {
            warn = "严重注意：该方法可能存在严重性能问题";
        } else if (seconds > 1L) {
            warn = "注意：该方法可能存在一般性能问题";
        } else {
            warn = "";
        }

        return String.format("3.返回值：%s \n统计耗时 : %s ms  %s \n================== web console log aspect end ===================", returnValueJson, after.getExecTime(), warn);
    }

    protected boolean canParse(Object arg) {
        return !(arg instanceof HttpServletRequest) && !(arg instanceof MultipartFile) && !(arg instanceof HttpSession) && !(arg instanceof HttpServletResponse);
    }

    protected abstract boolean prettyPrint();
}
