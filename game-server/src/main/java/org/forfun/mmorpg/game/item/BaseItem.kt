package org.forfun.mmorpg.game.item

import org.forfun.mmorpg.game.util.IdFactory

abstract class BaseItem {

    var id: Long = 0

    var modelId: Int = 0

    /**
     * owned count
     */
    var count: Int = 0

    /**
     * Returns <tt>true</tt> if it's binding item
     */
    var isBind: Boolean = false

    init {
        this.id = IdFactory.getNextId()
    }
}
