package org.forfun.mmorpg.game

interface Modules {

    companion object {
        // ------------------底层功能支持模块（-128 ~ -1）-----------------
        const val BASE: Short = -1
        const val GM: Short = -2
        const val CROSS: Short = -3

        // ------------------业务功能模块（1~326）---------------------

        /** 玩家 */
        const val PLAYER: Short = 1
        /** 场景 */
        const val SCENE: Short = 2
        /** 活动 */
        const val ACTIVITY: Short = 3
        /** 技能 */
        const val SKILL: Short = 4
        /** 聊天 */
        const val CHAT: Short = 5
    }
}
