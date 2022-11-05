package com.zoyo.web.aspect;

import com.zoyo.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: mxx
 * @Description:
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    /**
     * 配置切点，主要用于同类其他方法以此作为切入点
     * annotation 表示标注了某个注解的所有方法
     * execution：在方法执行是触发；*：返回任意类型；包路径；*：任意类；*(..)：任意参数
     */
    @Pointcut("@annotation(com.zoyo.web.aspect.LogAnnotation)")
//    @Pointcut("execution(* com.zoyo.web.controller.*.*(..))")
    public void myPointCut() {
    }

    /**
     * 环绕通知：执行调用前和调用后的任务
     *
     * @param joinPoint 表示可以执行目标方法
     * @return object
     */
    @Around("myPointCut()")
    public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 获取自定义注解里的值
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        String requestUri = HttpContextUtils.getHttpServletRequest().getRequestURI();
        log.info("------------------- " + requestUri + " AOP LOG START-------------------");
        if (logAnnotation != null) {
            log.info("module：{},`\t`operaDesc：{}",
                    logAnnotation.module(), logAnnotation.operaDesc());
        }

        // 获取请求方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method：{}", className + "." + methodName);

        //获取入参
        Object[] paramsObj = joinPoint.getArgs();
        if (paramsObj != null) {
            log.info("paramsObj:{}", Arrays.toString(paramsObj));
        }
        // 执行当前目标方法
        Object result = joinPoint.proceed();
        if (result != null) {
            log.info("返回参数: " + result.toString());
        }

        long endTime = System.currentTimeMillis();
        log.info("方法用时time= {}", (endTime - startTime) + "毫秒");
        log.info("------------------- " + requestUri + " AOP LOG END-------------------");
        return result;
    }

}
