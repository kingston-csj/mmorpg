package org.forfun.mmorpg.game

interface ServerLayer {

    fun init()

    fun onCenterServerConnected() {
        // 默认实现为空
    }
}
