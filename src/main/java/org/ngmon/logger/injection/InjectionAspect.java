package org.ngmon.logger.injection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

@Aspect
public class InjectionAspect {

    /**
     * Intercepts execution of all methods declared within subclasses of LogContext.
     */
    @Pointcut("execution(* org.ngmon.logger.injection.LogContext+.*(..)) && !execution(* org.ngmon.logger.injection.LogContext.*(..)) && target(logContext) && @annotation(org.ngmon.logger.annotation.Var)")
    public void allMethodsInContext(LogContext logContext) {}

    /**
     * Finds out method name and its parameter and injects the parameters values, then returns control to the intercepted method.
     */
    @Before(value = "allMethodsInContext(logContext)", argNames = "joinPoint, logContext")
    public void before(JoinPoint joinPoint, LogContext logContext) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter parameter = method.getParameters()[0];
        String name = method.getName();
        Type type = parameter.getParameterizedType();
        Object value = joinPoint.getArgs()[0];
        logContext.inject(name, type, value);
    }
}
