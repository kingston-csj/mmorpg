package org.forfun.mmorpg.rpc.data;

import java.util.Arrays;

public class RpcRequestData {

    private long index;

    private String serviceName;

    private Object[] extraParams;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object[] getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Object[] extraParams) {
        this.extraParams = extraParams;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "index=" + index +
                ", serviceName='" + serviceName + '\'' +
                ", extraParams=" + Arrays.toString(extraParams) +
                '}';
    }
}
