package org.forfun.mmorpg.rpc.codec;

public class RpcDataPackage {

    /**
     * rpc service
     */
    private String service;

    private String methodName;

    /**
     * request param
     * or
     * response content
     */
    private byte[] data;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
