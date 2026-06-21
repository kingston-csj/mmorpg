package org.forfun.mmorpg.game.jmx

import org.forfun.mmorpg.game.base.GameContext
import org.springframework.jmx.export.annotation.ManagedOperation
import org.springframework.jmx.export.annotation.ManagedResource
import org.springframework.stereotype.Component
import javax.script.ScriptEngineManager

@Component
@ManagedResource(objectName = "GameMXBean:name=gameMonitor")
open class GameMonitor : GameMonitorMBean {

    private val engineManager = ScriptEngineManager()

    private val groovyScriptEngine = engineManager.getEngineByName("groovy")

    override fun getOnlinePlayerSum(): Int {
        return GameContext.playerService().onlines.size
    }

    @ManagedOperation(description = "执行groovy脚本")
    override fun execGroovyScript(groovyCode: String): String {
        var msg = "执行成功"
        try {
            synchronized(engineManager) {
                val eval = groovyScriptEngine.eval(groovyCode)
                if (eval != null) {
                    msg = eval.toString()
                }
                return msg
            }
        } catch (e: Exception) {
            msg = e.message ?: "Unknown error"
        }
        return msg
    }
}
