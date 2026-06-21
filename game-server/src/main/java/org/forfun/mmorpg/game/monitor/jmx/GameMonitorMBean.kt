package org.forfun.mmorpg.game.monitor.jmx

/**
 * JMX游戏管理
 */
interface GameMonitorMBean {

    /**
     * total online players count
     */
    fun getOnlinePlayerSum(): Int

    /**
     * execute groovy code
     */
    fun execGroovyScript(groovyCode: String): String
}
