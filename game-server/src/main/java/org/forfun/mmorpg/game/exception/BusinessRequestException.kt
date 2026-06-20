package org.forfun.mmorpg.game.exception

/**
 * 业务请求执行异常
 */
class BusinessRequestException(var errorCode: Int) : RuntimeException()
