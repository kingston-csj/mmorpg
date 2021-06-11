package org.forfun.mmorpg.net.rpc;

public abstract class RpcCallbackResponse implements RpcMessage {

    protected int callbackId;

    public void setCallbackId(int callbackId) {
        this.callbackId = callbackId;
    }

    public int getCallbackId() {
        return callbackId;
    }
}
