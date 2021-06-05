package org.forfun.mmorpg.net.rpc;

public abstract class RpcCallbackRequest implements RpcMessage {

    protected int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
