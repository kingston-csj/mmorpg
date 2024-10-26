package org.forfun.mmorpg.game.item;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.util.IdFactory;

@Getter
@Setter
public abstract class BaseItem {

    private long id;
    /**
     *
     */
    private int modelId;
    /**
     * owned count
     */
    private int count;
    /**
     * Returns <tt>true</tt> if it's binding item
     */
    private boolean bind;

    public BaseItem() {
        this.id = IdFactory.getNextId();
    }


}