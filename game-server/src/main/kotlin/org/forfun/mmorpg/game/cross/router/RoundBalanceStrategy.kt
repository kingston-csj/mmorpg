package org.forfun.mmorpg.game.cross.router

import org.springframework.util.CollectionUtils
import java.util.concurrent.atomic.AtomicInteger

class RoundBalanceStrategy : BalanceStrategy {

    private val counter = AtomicInteger()

    override fun next(nodes: List<Int>): Int {
        if (CollectionUtils.isEmpty(nodes)) {
            return 0
        }
        return nodes[counter.getAndIncrement() % nodes.size]
    }
}
