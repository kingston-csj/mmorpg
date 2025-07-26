package org.forfun.mmorpg.game.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 监控aop执行长时间耗时
 */
@Component
@Aspect
public class JpaCostAspect {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Pointcut("execution(* org.forfun.mmorpg.game.database.user.dao.*Dao.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object obj = joinPoint.proceed(args);
        String returnTypes = "";
        if (obj != null) {
            returnTypes = obj.getClass().getSimpleName();
        }

        long endTime = System.currentTimeMillis();
        long cost = endTime - startTime;
        if (cost > 50) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
            logger.info("方法{}()->{}执行耗时{}毫秒", methodName, returnTypes, cost);
        }

        return obj;
    }

}
