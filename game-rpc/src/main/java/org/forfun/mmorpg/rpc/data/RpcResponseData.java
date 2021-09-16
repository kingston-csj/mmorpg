package org.forfun.mmorpg.rpc.data;

public class RpcResponseData {

    private long index;

    private Object response;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
