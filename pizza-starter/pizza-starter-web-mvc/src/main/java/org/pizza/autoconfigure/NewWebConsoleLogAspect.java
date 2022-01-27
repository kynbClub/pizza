//package org.pizza.autoconfigure;
//
//import org.pizza.common.web.mvc.aspect.WebConsoleLogAspect;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
///**
// * @author 高巍
// * @since 2020-04-09 10:23 上午
// */
//@Aspect
//@Component
//public class NewWebConsoleLogAspect extends WebConsoleLogAspect {
//
//    @Around("execution(* org.pizza.*.controller.*Controller.*(..))")
//    public Object aroundControllerMethod(final ProceedingJoinPoint pjp) throws Throwable {
//        return around(pjp);
//    }
//}
