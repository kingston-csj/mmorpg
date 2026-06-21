package org.forfun.mmorpg.game.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * 监控aop执行长时间耗时
 */
@Component
@Aspect
open class JpaCostAspect {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @Pointcut("execution(* org.forfun.mmorpg.game.database.user.dao.*Dao.*(..))")
    fun pointCut() {}

    @Around("pointCut()")
    @Throws(Throwable::class)
    fun doPointCut(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args
        val startTime = System.currentTimeMillis()
        val obj = joinPoint.proceed(args)
        var returnTypes = ""
        if (obj != null) {
            returnTypes = obj.javaClass.simpleName
        }

        val endTime = System.currentTimeMillis()
        val cost = endTime - startTime
        if (cost > 50) {
            val signature = joinPoint.signature as MethodSignature
            val methodName = signature.declaringTypeName + "." + signature.name
            logger.info("方法$methodName()->$returnTypes 执行耗时${cost}毫秒")
        }

        return obj
    }
}
