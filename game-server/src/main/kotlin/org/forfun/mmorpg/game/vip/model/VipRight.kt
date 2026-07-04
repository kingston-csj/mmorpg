package org.forfun.mmorpg.game.vip.model

import java.util.HashSet

class VipRight {

    var level: Int = 0
    var exp: Int = 0
    var rewardedIds: MutableSet<Int> = HashSet()

    override fun toString(): String {
        return "VipRight [level=$level, exp=$exp]"
    }
}