package org.forfun.mmorpg.game.cross.router

import org.springframework.util.CollectionUtils
import java.util.Random

class RandomBalanceStrategy : BalanceStrategy {

    private val random = Random()

    override fun next(nodes: List<Int>): Int {
        if (CollectionUtils.isEmpty(nodes)) {
            return 0
        }
        return nodes[random.nextInt() % nodes.size]
    }
}
