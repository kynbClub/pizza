package org.pizza.common.web.aspect;


import cn.hutool.json.JSONUtil;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.pizza.common.web.model.BaseLog;

import java.util.Arrays;
import java.util.HashMap;

/**
 * AOP切面日志父类
 *
 * @author gv
 */

public abstract class BaseLogAspect {


    /**
     * 环绕通配符方式
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        BaseLog before = this.before(pjp);
        Object returnVal = pjp.proceed();
        this.after(returnVal, before);
        return returnVal;
    }


    /**
     * 方法前执行
     */
    protected BaseLog before(JoinPoint joinPoint) throws NotFoundException {
        Class<?> clazz = joinPoint.getTarget().getClass();
        //获取类名
        String className = clazz.getName();
        //获取方法
        String methodName = joinPoint.getSignature().getName();
        //方法参数
        HashMap<String, Object> params = this.params(this.fields(clazz, className, methodName), joinPoint.getArgs());
        //封装日志记录
        BaseLog baseLog = new BaseLog();
        baseLog.setMethodClass(className);
        baseLog.setMethodName(methodName);
        baseLog.setMethodParam(JSONUtil.toJsonStr(params));
        baseLog.setReturnValue("");
        baseLog.setStartTime(System.currentTimeMillis());
        baseLog.setEndTime(0L);
        return baseLog;
    }


    /**
     * 返回后执行
     *
     * @param returnVal
     */
    protected BaseLog after(Object returnVal, BaseLog baseLog) {
        baseLog.setEndTime(System.currentTimeMillis());
        baseLog.setReturnValue(JSONUtil.toJsonStr(returnVal));
        baseLog.setExecTime(System.currentTimeMillis() - baseLog.getStartTime());
        return baseLog;
    }


    /**
     * 反射得到方法参数的名称
     *
     * @param clazz
     * @param clazzName
     * @param methodName
     * @return
     * @throws NotFoundException
     */
    protected String[] fields(Class clazz, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return new String[]{};
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
            //paramNames即参数名
            paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
    }

    protected HashMap<String, Object> params(String[] fieldNames, Object[] args) {
        HashMap<String, Object> param = new HashMap<String, Object>(fieldNames.length);
        for (int i = 0; i < fieldNames.length; i++) {
            //null
            if (args[i] == null) {
                param.put(fieldNames[i], null);
            }
            //array
            else if (args[i].getClass().isArray()) {
                param.put(fieldNames[i], Arrays.toString((Object[]) args[i]));
            }
            //canParse
            else if (!canParse(args[i])
            ) {
                param.put(fieldNames[i], args[i].getClass().getSimpleName());
            }
            //ok
            else {
                param.put(fieldNames[i], args[i]);
            }
        }
        return param;
    }

    /**
     * 可以解析
     *
     * @param arg
     * @return
     */
    protected abstract boolean canParse(Object arg);


}



