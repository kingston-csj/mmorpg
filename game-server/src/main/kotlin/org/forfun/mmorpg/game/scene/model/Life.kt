package org.forfun.mmorpg.game.scene.model

class Life(var maxHp: Long, var maxMp: Long) {

    var currHp: Long = maxHp
        set(value) {
            field = value
        }

    var currMp: Long = maxMp
        set(value) {
            field = value
        }

    fun reduceHp(changeValue: Long): Long {
        return if (changeValue <= 0) {
            this.currHp
        } else {
            this.currHp - changeValue
        }
    }

    fun isDead(): Boolean = this.currHp <= 0
}
