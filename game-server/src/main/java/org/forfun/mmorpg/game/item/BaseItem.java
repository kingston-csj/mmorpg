package org.forfun.mmorpg.game.item;

import org.forfun.mmorpg.game.util.IdFactory;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isBind() {
        return bind;
    }

    public void setBind(boolean bind) {
        this.bind = bind;
    }

}