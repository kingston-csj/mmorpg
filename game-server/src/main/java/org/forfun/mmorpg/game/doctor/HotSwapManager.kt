package org.forfun.mmorpg.game.doctor

import jakarta.annotation.PostConstruct
import jforgame.commons.util.FileUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import com.sun.tools.attach.VirtualMachine
import groovy.lang.GroovyClassLoader
import java.lang.management.ManagementFactory

@Component
open class HotSwapManager {

    private val logger = LoggerFactory.getLogger(HotSwapManager::class.java)

    companion object {
        private lateinit var self: HotSwapManager
        private const val EXEC_SUCC = "热更成功"
        private const val EXEC_FAILED = "热更失败"
    }

    fun getInstance(): HotSwapManager = self

    @PostConstruct
    private fun init() {
        self = HotSwapManager()
    }

    fun hotSwap(fileName: String): String {
        if ("Common".equals(fileName, ignoreCase = true)) {
            return hotSwapScript()
        }

        return if (reloadClass(fileName)) {
            EXEC_SUCC
        } else {
            EXEC_FAILED
        }
    }

    /**
     * 使用groovy类加载器重载java代码 重载的java文件可以直接使用源文件，无需编译为class
     * @return
     */
    fun hotSwapScript(): String {
        return try {
            val filePath = "hotswap/CommonScript.java"
            val clazzFile = FileUtil.readFullText(filePath)

            @Suppress("UNCHECKED_CAST")
            val clazz = GroovyClassLoader().parseClass(clazzFile)
            clazz.newInstance()
            EXEC_SUCC
        } catch (e: Exception) {
            logger.error("", e)
            EXEC_FAILED
        }
    }

    /**
     * 使用jdk instrument 来重新加载内存中的class 你只能修改方法体的代码，而不能动态增删方法
     * @param className
     * @return
     */
    private fun reloadClass(className: String): Boolean {
        return try {
            // 拿到当前jvm的进程id
            val pid = ManagementFactory.getRuntimeMXBean().name.split("@")[0]
            val vm = VirtualMachine.attach(pid)
            val classStr = className.split("\\.")
            val path = "./hotswap/" + classStr[classStr.size - 1] + ".class"
            System.err.println("path==$path")
            // path参数即agentmain()方法的第一个参数
            vm.loadAgent("libs/jforgame-hotswap-agent", path)
            true
        } catch (e: Exception) {
            logger.error("", e)
        } as Boolean
    }
}
