package org.forfun.mmorpg.game.gm

import jforgame.socket.support.MessageExecuteUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.Ordered
import org.springframework.stereotype.Component

@Component
open class GmHandlerBeanPostProcessor : BeanPostProcessor, ApplicationContextAware, Ordered {

    @Autowired
    private lateinit var gmDispatcher: GmDispatcher

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        // 默认实现
    }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        try {
            val clz = bean.javaClass
            val methods = clz.declaredMethods
            for (method in methods) {
                val mapperAnnotation = method.getAnnotation(GmHandler::class.java)
                if (mapperAnnotation != null) {
                    val cmdExecutor = MessageExecuteUnit.valueOf(method, method.parameterTypes, bean)
                    gmDispatcher.registerHandler(mapperAnnotation.cmd.name, cmdExecutor)
                }
            }
        } catch (e: Exception) {
            throw e
        }

        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        return bean
    }

    override fun getOrder(): Int {
        return Integer.MIN_VALUE
    }
}
