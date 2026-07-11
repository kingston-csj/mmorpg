package org.forfun.mmorpg.game.buff.model

import java.util.concurrent.ConcurrentHashMap

class BuffContainer {

    private val buffs = ConcurrentHashMap<Int, Buff>()

    fun addBuff(buff: Buff) {
        buffs[buff.modelId] = buff
    }

    fun getBuff(buffId: Int): Buff? {
        return buffs[buffId]
    }

    fun removeBuff(buff: Buff): Buff? {
        return buffs.remove(buff.modelId)
    }

    fun removeBuff(buffId: Int): Buff? {
        return buffs.remove(buffId)
    }

    fun getAllBuffs(): Collection<Buff> {
        return buffs.values
    }

    fun containsBuff(buffId: Int): Boolean {
        return buffs.containsKey(buffId)
    }

    fun clear() {
        buffs.clear()
    }
}