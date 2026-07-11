package org.forfun.mmorpg.game.scene.model

/**
 * 场景刷怪模式枚举
 */
enum class SpawnMode {
    /** 时间刷新：固定间隔毫秒刷新（野外常规怪） */
    TIME,
    /** 回合刷新：场景/副本回合触发刷新（副本波次怪） */
    ROUND,
    /** 一次性刷新：场景加载/玩家进入只刷新一次（场景静态怪） */
    ONCE
}