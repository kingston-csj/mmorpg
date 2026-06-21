package org.forfun.mmorpg.game.cross.router

interface BalanceStrategy {

    fun next(nodes: List<Int>): Int
}
