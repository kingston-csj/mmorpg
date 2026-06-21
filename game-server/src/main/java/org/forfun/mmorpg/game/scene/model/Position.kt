package org.forfun.mmorpg.game.scene.model

data class Position(val x: Int, val y: Int) {

    override fun toString(): String {
        return "($x, $y)"
    }
}
