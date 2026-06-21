package org.forfun.mmorpg.game.gm

/**
 * gm命令处理器
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GmHandler(val cmd: GmCommands)
